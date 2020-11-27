package com.innova.doctrro.bs.dao.impl;


import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.bs.dao.ReactiveBookingSlotDao;
import com.innova.doctrro.bs.dao.repository.ReactiveBookingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;

@Component
public class ReactiveBookingSlotDaoImpl implements ReactiveBookingSlotDao {

    private final ReactiveBookingSlotRepository slotRepository;

    @Autowired
    public ReactiveBookingSlotDaoImpl(ReactiveBookingSlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    @Override
    public Flux<BookingSlot> create(List<BookingSlot> slots) {
        return slotRepository.insert(slots);
    }

    @Override
    public Mono<BookingSlot> create(BookingSlot item) {
        return slotRepository.insert(item);
    }

    @Override
    public Mono<BookingSlot> findById(String s) {
        return slotRepository.findById(s);
    }

    @Override
    public Flux<BookingSlot> findAllFutureByFacilityIdAndDoctorId(String facilityId, String doctorId, LocalDateTime time) {
        return slotRepository.findAllByFacilityIdAndDoctorIdAndAndStartTimeAfter(facilityId, doctorId, time);
    }

    @Override
    public Flux<BookingSlot> findAll() {
        return slotRepository.findAll();
    }

    @Override
    public Mono<BookingSlot> update(String s, BookingSlot item) {
        return slotRepository.save(item);
    }

    @Override
    public Mono<Void> remove(BookingSlot item) {
        return slotRepository.delete(item);
    }

    @Override
    public Mono<Void> remove() {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }
}
