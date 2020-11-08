package com.innova.doctrro.bs.dao.repository;

import com.innova.doctrro.bs.beans.BookingSlot;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface ReactiveBookingSlotRepository extends ReactiveMongoRepository<BookingSlot, String> {

    Flux<BookingSlot> findAllByFacilityIdAndDoctorIdAndAndStartTimeAfter(String facilityId, String doctorId, LocalDateTime time);
}
