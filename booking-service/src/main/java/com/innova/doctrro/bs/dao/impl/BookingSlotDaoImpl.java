package com.innova.doctrro.bs.dao.impl;


import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.bs.dao.BookingSlotDao;
import com.innova.doctrro.bs.dao.repository.BookingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingSlotDaoImpl implements BookingSlotDao {

    private final BookingSlotRepository slotRepository;

    @Autowired
    public BookingSlotDaoImpl(BookingSlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    @Override
    public List<BookingSlot> create(List<BookingSlot> slots) {
        return null;
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
