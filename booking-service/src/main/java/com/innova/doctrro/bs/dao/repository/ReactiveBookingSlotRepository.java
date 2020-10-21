package com.innova.doctrro.bs.dao.repository;

import com.innova.doctrro.bs.beans.BookingSlot;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReactiveBookingSlotRepository extends ReactiveMongoRepository<BookingSlot, String> {
}
