package com.innova.doctrro.bs.dao;

import com.innova.doctrro.bs.beans.Booking;
import com.innova.doctrro.common.dao.ReactiveGenericDao;
import reactor.core.publisher.Flux;

public interface ReactiveBookingDao extends ReactiveGenericDao<Booking, String> {

    Flux<Booking> findAllByBookedUserEmail(String email);
    Flux<Booking> findAllByPractitionerRegId(String regId);

}
