package com.innova.doctrro.bs.dao.repository;

import com.innova.doctrro.bs.beans.Booking;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReactiveBookingRepository extends ReactiveMongoRepository<Booking, String> {
}
