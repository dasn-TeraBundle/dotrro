package com.innova.doctrro.bs.dao.impl;

import com.innova.doctrro.bs.beans.Booking;
import com.innova.doctrro.bs.dao.ReactiveBookingDao;
import com.innova.doctrro.bs.dao.repository.ReactiveBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;


@Component
public class ReactiveBookingDaoImpl implements ReactiveBookingDao {

    private final ReactiveBookingRepository bookingRepository;

    @Autowired
    public ReactiveBookingDaoImpl(ReactiveBookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Mono<Booking> create(Booking item) {
        return bookingRepository.insert(item);
    }

    @Override
    public Mono<Booking> findById(String s) {
        return bookingRepository.findById(s);
    }

    @Override
    public Flux<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Mono<Booking> update(String s, Booking item) {
        return bookingRepository.save(item);
    }

    @Override
    public Mono<Void> remove(String s) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove(Booking item) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove() {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }
}
