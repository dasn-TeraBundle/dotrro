package com.innova.doctrro.bs.dao.repository;

import com.innova.doctrro.bs.beans.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, String> {
}
