package com.innova.doctrro.bs.dao.repository;

import com.innova.doctrro.bs.beans.Booking;
import com.innova.doctrro.bs.beans.BookingStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.stream.Stream;

public interface BookingRepository extends MongoRepository<Booking, String> {

    Stream<Booking> findAllByBookedBy_Email(String email);
    Stream<Booking> findAllByPractioner_RegIdAndStatusNotIn(String regId, List<BookingStatus> statuses);
}
