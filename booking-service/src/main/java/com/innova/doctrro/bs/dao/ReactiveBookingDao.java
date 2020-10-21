package com.innova.doctrro.bs.dao;

import com.innova.doctrro.bs.beans.Booking;
import com.innova.doctrro.bs.beans.BookingStatus;
import com.innova.doctrro.common.dao.ReactiveGenericDao;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

public interface ReactiveBookingDao extends ReactiveGenericDao<Booking, String> {

    Flux<Booking> findAllByBookedUserEmail(String email);
    Flux<Booking> findAllByPractitionerRegId(String regId, List<BookingStatus> statuses);
    Flux<Booking> findAllByStatusAndCreatedOnBefore(BookingStatus status, LocalDateTime time);

}
