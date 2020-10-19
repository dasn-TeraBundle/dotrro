package com.innova.doctrro.bs.service;


import com.innova.doctrro.bs.beans.Booking;
import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.bs.dao.ReactiveBookingDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.innova.doctrro.bs.beans.BookingStatus.FAILED;
import static com.innova.doctrro.bs.beans.BookingStatus.INITIATED;
import static com.innova.doctrro.bs.beans.SlotStatus.AVAILABLE;
import static com.innova.doctrro.bs.beans.SlotStatus.LOCKED;

@EnableScheduling
@Component
class BookingSchedulerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingSchedulerService.class);

    private final ReactiveBookingDao bookingDao;
    private final ReactiveBookingSlotService bookingSlotService;

    @Value("${booking.scheduler.createTimeThreshold}")
    private int createTimeThreshold;

    @Autowired
    public BookingSchedulerService(ReactiveBookingDao bookingDao, ReactiveBookingSlotService bookingSlotService) {
        this.bookingDao = bookingDao;
        this.bookingSlotService = bookingSlotService;
    }

    @Scheduled(cron = "0 * * * * *")
    private void releaseLockedSlots() {
        var now = LocalDateTime.now();
        var threshold = now.minusMinutes(createTimeThreshold);

//        bookingDao.findAll()
//                .filter(booking -> booking.getStatus() == BookingStatus.INITIATED && (booking.getCreatedOn() == null ||
//                        (threshold.isAfter(booking.getCreatedOn()) &&
//                                (booking.getUpdatedOn() == null || threshold.isAfter(booking.getUpdatedOn())) ))
//                )
        bookingDao.findAllByStatusAndCreatedOnBefore(INITIATED, threshold)
                .flatMap(booking ->
                        Flux.combineLatest(
                                Flux.just(booking),
                                bookingSlotService.findById(booking.getSlotId()),
                                (b, bs) -> Arrays.asList(b, bs)
                        )
                )
                .filter(v -> {
                    BookingSlot slot = (BookingSlot) v.get(1);
                    return slot.getStatus() == LOCKED;
                })
                .flatMap(v -> {
                    Booking booking = (Booking) v.get(0);
                    BookingSlot slot = (BookingSlot) v.get(1);

                    booking.setStatus(FAILED);
                    slot.setStatus(AVAILABLE);

                    return Flux.combineLatest(
                            bookingDao.update(booking.getId(), booking),
                            bookingSlotService.update(slot.getId(), slot),
                            (b, bs) -> Arrays.asList(b, bs)
                    );
                })
                .subscribe(v -> {
                            Booking booking = (Booking) v.get(0);
                            BookingSlot slot = (BookingSlot) v.get(1);

                            LOGGER.info("Booking id {} marked FAILED and slot {} now AVAILABLE", booking.getId(), slot.getId());
                        },
                        err -> LOGGER.error("Error Occurred", err),
                        () -> LOGGER.info("Booking scheduler completed"));
    }
}
