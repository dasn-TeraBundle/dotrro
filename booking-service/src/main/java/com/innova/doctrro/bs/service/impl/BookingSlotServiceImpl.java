package com.innova.doctrro.bs.service.impl;

import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.bs.dao.BookingSlotDao;
import com.innova.doctrro.bs.service.BookingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookingSlotServiceImpl implements BookingSlotService {

    private final BookingSlotDao bookingSlotDao;

    @Autowired
    public BookingSlotServiceImpl(BookingSlotDao bookingSlotDao) {
        this.bookingSlotDao = bookingSlotDao;
    }

    @Override
    public BookingSlot create(BookingSlot item) {
        return null;
    }

    @Override
    public BookingSlot findById(String s) {
        return null;
    }

    @Override
    public List<BookingSlot> findAll() {
        return null;
    }

    @Override
    public BookingSlot update(String s, BookingSlot item) {
        return null;
    }

    @Override
    public void remove(String s) {

    }

    @Override
    public void remove(BookingSlot item) {

    }

    @Override
    public void remove() {

    }
}
