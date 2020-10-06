package com.innova.doctrro.bs.dao.impl;

import com.innova.doctrro.bs.beans.Booking;
import com.innova.doctrro.bs.dao.BookingDao;
import com.innova.doctrro.bs.dao.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BookingDaoImpl implements BookingDao {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingDaoImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking create(Booking item) {
        return null;
    }

    @Override
    public Booking findById(String s) {
        return null;
    }

    @Override
    public List<Booking> findAll() {
        return null;
    }

    @Override
    public Booking update(String s, Booking item) {
        return null;
    }

    @Override
    public void remove(String s) {

    }

    @Override
    public void remove(Booking item) {

    }

    @Override
    public void remove() {

    }
}
