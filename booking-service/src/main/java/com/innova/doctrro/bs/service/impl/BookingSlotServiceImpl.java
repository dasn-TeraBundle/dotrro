package com.innova.doctrro.bs.service.impl;

import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.bs.dao.BookingSlotDao;
import com.innova.doctrro.bs.exception.BookingSlotDBExceptionFactory;
import com.innova.doctrro.bs.service.BookingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.innova.doctrro.common.constants.DBExceptionType.DATA_NOT_FOUND;
import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;


@Service
public class BookingSlotServiceImpl implements BookingSlotService {

    private final BookingSlotDao bookingSlotDao;

    @Autowired
    public BookingSlotServiceImpl(BookingSlotDao bookingSlotDao) {
        this.bookingSlotDao = bookingSlotDao;
    }

    @Override
    public BookingSlot create(BookingSlot item) {
        return bookingSlotDao.create(item);
    }

    @Override
    public BookingSlot findById(String s) {
        BookingSlot slot = bookingSlotDao.findById(s);
        if (slot == null) {
            throw BookingSlotDBExceptionFactory.createException(DATA_NOT_FOUND);
        }

        return slot;
    }

    @Override
    public List<BookingSlot> findAll() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }

    @Override
    public BookingSlot update(String s, BookingSlot item) {
        return bookingSlotDao.update(s, item);
    }

    @Override
    public void remove(String s) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }
}
