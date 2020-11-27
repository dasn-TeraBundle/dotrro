package com.innova.doctrro.bs.dao.impl;

import com.innova.doctrro.bs.beans.Booking;
import com.innova.doctrro.bs.beans.BookingStatus;
import com.innova.doctrro.bs.dao.BookingDao;
import com.innova.doctrro.bs.dao.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;


@Component
public class BookingDaoImpl implements BookingDao {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingDaoImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking create(Booking item) {
        return bookingRepository.insert(item);
    }

    @Override
    public Booking findById(String s) {
        return bookingRepository.findById(s).orElse(null);
    }

    @Override
    public Supplier<Stream<Booking>> findAllByBookedUserEmail(String email) {
        return () -> bookingRepository.findAllByBookedBy_Email(email);
    }

    @Override
    public Supplier<Stream<Booking>> findAllByPractitionerRegId(String regId, List<BookingStatus> statuses) {
        return () -> bookingRepository.findAllByPractioner_RegIdAndStatusNotIn(regId, statuses);
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking update(String s, Booking item) {
        return bookingRepository.save(item);
    }

    @Override
    public void remove(Booking item) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }
}
