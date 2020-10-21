package com.innova.doctrro.bs.dao.repository;

import com.innova.doctrro.bs.beans.Booking;
import com.innova.doctrro.bs.beans.BookingStatus;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

public interface ReactiveBookingRepository extends ReactiveMongoRepository<Booking, String> {

    Flux<Booking> findAllByBookedBy_Email(String email);
    Flux<Booking> findAllByPractioner_RegIdAndStatusNotIn(String regId, List<BookingStatus> statuses);
    Flux<Booking> findAllByStatusAndCreatedOnBefore(BookingStatus status, LocalDateTime time);

}
