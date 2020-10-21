package com.innova.doctrro.bs.dao.repository;

import com.innova.doctrro.bs.beans.BookingSlot;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingSlotRepository extends MongoRepository<BookingSlot, String> {
}
