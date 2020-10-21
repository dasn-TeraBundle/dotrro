package com.innova.doctrro.bs.dao.impl;


import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.bs.dao.BookingSlotDao;
import com.innova.doctrro.bs.dao.repository.BookingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;

@Component
public class BookingSlotDaoImpl implements BookingSlotDao {

    private final BookingSlotRepository slotRepository;

    @Autowired
    public BookingSlotDaoImpl(BookingSlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    @Override
    public List<BookingSlot> create(List<BookingSlot> slots) {
        return slotRepository.insert(slots);
    }

    @Override
    public BookingSlot create(BookingSlot item) {
        return slotRepository.insert(item);
    }

    @Override
    public BookingSlot findById(String s) {
        return slotRepository.findById(s).orElse(null);
    }

    @Override
    public List<BookingSlot> findAll() {
        return slotRepository.findAll();
    }

    @Override
    public BookingSlot update(String s, BookingSlot item) {
        return slotRepository.save(item);
    }

    @Override
    public void remove(String s) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }

    @Override
    public void remove(BookingSlot item) {
        slotRepository.delete(item);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }
}
