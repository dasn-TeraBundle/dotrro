package com.innova.doctrro.bs.service.impl;

import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.bs.dao.ReactiveBookingSlotDao;
import com.innova.doctrro.bs.exception.BookingSlotDBExceptionFactory;
import com.innova.doctrro.bs.service.ReactiveBookingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static com.innova.doctrro.common.constants.DBExceptionType.DATA_NOT_FOUND;
import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;


@Service
public class ReactiveBookingSlotServiceImpl implements ReactiveBookingSlotService {

    private final ReactiveBookingSlotDao bookingSlotDao;

    @Autowired
    public ReactiveBookingSlotServiceImpl(ReactiveBookingSlotDao bookingSlotDao) {
        this.bookingSlotDao = bookingSlotDao;
    }

    @Override
    public Flux<BookingSlot> create(List<BookingSlot> items) {
        return bookingSlotDao.create(items);

    }

    @Override
    public Mono<BookingSlot> create(BookingSlot item) {
        return bookingSlotDao.create(item);
    }

    @Override
    public Mono<BookingSlot> findById(String s) {
        return bookingSlotDao.findById(s)
                .switchIfEmpty(Mono.defer(() -> Mono.error(BookingSlotDBExceptionFactory.createException(DATA_NOT_FOUND))))
                .map(slot -> slot);
    }

    @Override
    public Flux<BookingSlot> findAllFutureSLotsByFacilityIdAndDoctorId(String facilityId, String doctorId) {
        var now = LocalDateTime.now();
        return bookingSlotDao.findAllFutureByFacilityIdAndDoctorId(facilityId, doctorId, now);
    }

    @Override
    public Flux<BookingSlot> findAll() {
        return Flux.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<BookingSlot> update(String s, BookingSlot item) {
        return bookingSlotDao.update(s, item);
    }

    @Override
    public Mono<Void> remove(String s) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove() {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }
}
