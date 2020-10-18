package com.innova.doctrro.bs.dao;

import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.common.dao.ReactiveGenericDao;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ReactiveBookingSlotDao extends ReactiveGenericDao<BookingSlot, String> {

    Flux<BookingSlot> create(List<BookingSlot> slots);
}
