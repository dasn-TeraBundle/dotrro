package com.innova.doctrro.bs.dao;

import com.innova.doctrro.bs.beans.Booking;
import com.innova.doctrro.bs.beans.BookingStatus;
import com.innova.doctrro.common.dao.GenericDao;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface BookingDao extends GenericDao<Booking, String> {

    Supplier<Stream<Booking>> findAllByBookedUserEmail(String email);
    Supplier<Stream<Booking>> findAllByPractitionerRegId(String regId, List<BookingStatus> statuses);
}
