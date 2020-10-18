package com.innova.doctrro.bs.service;

import com.innova.doctrro.bs.beans.BookingStatus;
import com.innova.doctrro.common.constants.PaymentStatus;
import com.innova.doctrro.common.service.GenericService;
import com.innova.doctrro.common.service.ReactiveGenericService;
import reactor.core.publisher.Mono;

import static com.innova.doctrro.bs.dto.BookingDto.BookingDtoRequest;
import static com.innova.doctrro.bs.dto.BookingDto.BookingDtoResponse;

public interface ReactiveBookingService extends ReactiveGenericService<BookingDtoRequest, BookingDtoResponse, String> {

    Mono<Boolean> lockSlot(String slotId, String email);
    Mono<BookingDtoResponse> update(String bookingId, BookingStatus status);
    Mono<Void> update(String bookingId, PaymentStatus status);
}
