package com.innova.doctrro.docs.service;


import com.innova.doctrro.common.dto.FacilityDto;
import com.innova.doctrro.common.dto.NewSlotEvent;
import com.innova.doctrro.common.service.KafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
@Scope("prototype")
public class NewSlotTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewSlotTask.class);
    private static final String KAFKA_SLOT_TOPIC = "slots_create";
    private static final int ADVANCE_MONTHS = 1;

    private final FacilityDto.FacilityDtoResponse dtoResponse;
    private final FacilityDto.Practitioner practitioner;
    private final boolean init;

    @Autowired
    private KafkaService kafkaService;

    public NewSlotTask(FacilityDto.FacilityDtoResponse dtoResponse, FacilityDto.Practitioner practitioner, boolean init) {
        this.dtoResponse = dtoResponse;
        this.practitioner = practitioner;
        this.init = init;
    }

    @Override
    public void run() {
        List<NewSlotEvent> allSlots;
        if (init) {
            allSlots = getFullMonthSlots();
        } else {
            allSlots = getDaySlotAfterMonth();
        }

        try {
            if (!allSlots.isEmpty()) {
                kafkaService.send(KAFKA_SLOT_TOPIC, allSlots);
            }
        } catch (Exception ex) {
            LOGGER.error("Error creating new slot events", ex);
        }
    }

    private List<NewSlotEvent> getFullMonthSlots() {
        return practitioner.getSlots().stream()  //Loop over all slots from db
                    .flatMap(slot -> {
                        var start = LocalDate.now().with(TemporalAdjusters.next(slot.getDayOfWeek()));
                        var end = start.plusMonths(ADVANCE_MONTHS);
                        byte duration = slot.getDuration();

                        //Loop over each day in week for a db slot
                        return Stream.iterate(start, d -> d.isBefore(end), d -> d.plusDays(7))
                                .flatMap(d -> {
                                    LocalTime startTime = LocalTime.parse(slot.getStartTime());
                                    LocalTime endTime = LocalTime.parse(slot.getEndTime());

                                    //Loop over each time frame in a day
                                    return Stream.iterate(startTime, t -> t.isBefore(endTime), t -> t.plusMinutes(duration))
                                            .map(t -> {
                                                var dt = LocalDateTime.of(d, t);

                                                return mapSlotToSlotEvent(slot, duration, dt);
                                            });
                                });
                    }).collect(Collectors.toList());
    }

    private List<NewSlotEvent> getDaySlotAfterMonth() {
        var date = LocalDate.now().plusMonths(ADVANCE_MONTHS);

        return practitioner.getSlots().stream()
                .filter(slot -> slot.getDayOfWeek() == date.getDayOfWeek())
                .flatMap(slot -> {
                    LocalTime startTime = LocalTime.parse(slot.getStartTime());
                    LocalTime endTime = LocalTime.parse(slot.getEndTime());
                    byte duration = slot.getDuration();

                    return Stream.iterate(startTime, t -> t.isBefore(endTime), t -> t.plusMinutes(duration))
                            .map(t -> {
                                var dt = LocalDateTime.of(date, t);

                                return mapSlotToSlotEvent(slot, duration, dt);
                            });
                }).collect(Collectors.toList());
    }

    private NewSlotEvent mapSlotToSlotEvent(FacilityDto.Practitioner.Slot slot, byte duration, LocalDateTime dt) {
        var slotEvent = new NewSlotEvent();
        slotEvent.setFacilityId(dtoResponse.getId());
        slotEvent.setFacilityName(dtoResponse.getName());
        slotEvent.setDoctorId(practitioner.getRegId());
        slotEvent.setDoctorName(practitioner.getName());
        slotEvent.setStartTime(dt.toString());
        slotEvent.setEndTime(dt.plusMinutes(duration).toString());
        slotEvent.setCharge(slot.getFee());
        slotEvent.setAutoApproveEnabled(slot.isAutoApproveEnabled());
        return slotEvent;
    }
}
