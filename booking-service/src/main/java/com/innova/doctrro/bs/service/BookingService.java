package com.innova.doctrro.bs.service;

import com.innova.doctrro.bs.beans.BookingStatus;
import com.innova.doctrro.common.constants.PaymentStatus;
import com.innova.doctrro.common.service.GenericService;

import static com.innova.doctrro.bs.dto.BookingDto.BookingDtoRequest;
import static com.innova.doctrro.bs.dto.BookingDto.BookingDtoResponse;

public interface BookingService extends GenericService<BookingDtoRequest, BookingDtoResponse, String> {

    BookingDtoResponse update(String bookingId, BookingStatus status);
    void update(String bookingId, PaymentStatus status);
}
