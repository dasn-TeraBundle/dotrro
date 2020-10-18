package com.innova.doctrro.bs.dao.repository;

import com.innova.doctrro.bs.beans.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.stream.Stream;

public interface BookingRepository extends MongoRepository<Booking, String> {

    Stream<Booking> findAllByBookedBy_Email(String email);
    Stream<Booking> findAllByPractioner_RegId(String regId);
}
