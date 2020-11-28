package com.innova.doctrro.bs.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.bs.beans.SlotStatus;
import com.innova.doctrro.common.dto.NewSlotEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
class BookingKafkaListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingKafkaListener.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final ReactiveBookingSlotService bookingSlotService;

    @Autowired
    BookingKafkaListener(ReactiveBookingSlotService bookingSlotService) {
        this.bookingSlotService = bookingSlotService;
    }

    @KafkaListener(topics = "slots_create", groupId = "slot_c_grp")
    void listenNewSlots(String message) {
        LOGGER.info("Message : {}", message);

        try {
            NewSlotEvent[] events = MAPPER.readValue(message, NewSlotEvent[].class);
            List<BookingSlot> slots = Arrays.stream(events)
                    .map(event -> {
                        var slot = new BookingSlot();

                        slot.setFacilityId(event.getFacilityId());
                        slot.setFacilityName(event.getFacilityName());
                        slot.setDoctorId(event.getDoctorId());
                        slot.setDoctorName(event.getDoctorName());
                        slot.setStartTime(LocalDateTime.parse(event.getStartTime()));
                        slot.setEndTime(LocalDateTime.parse(event.getEndTime()));
                        slot.setBookingEnabled(event.isBookingEnabled());
                        slot.setStatus(SlotStatus.AVAILABLE);
                        slot.setCharge(event.getCharge());
                        slot.setAutoApproveEnabled(event.isAutoApproveEnabled());

                        return slot;
                    }).collect(Collectors.toList());

            bookingSlotService.create(slots)
                    .subscribe(
                            slot -> LOGGER.info("Created slot {}", slot.getId()),
                            err -> {
                                if (!(err instanceof DuplicateKeyException)) {
                                    LOGGER.error("Error creating slot", err);
                                } else {
                                    LOGGER.error("Slots already created earlier");
                                }
                            });
        } catch (JsonProcessingException e) {
            LOGGER.error("Error while processing", e);
        }
    }
}
