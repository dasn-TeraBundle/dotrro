package com.innova.doctrro.bs.service;

import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.common.service.ReactiveGenericService;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ReactiveBookingSlotService extends ReactiveGenericService<BookingSlot, BookingSlot, String> {

    Flux<BookingSlot> create(List<BookingSlot> items);
    Flux<BookingSlot> findAllFutureSLotsByFacilityIdAndDoctorId(String facilityId, String doctorId);

}
