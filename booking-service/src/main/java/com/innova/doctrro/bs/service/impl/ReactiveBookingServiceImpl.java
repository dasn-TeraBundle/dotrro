package com.innova.doctrro.bs.service.impl;

import com.innova.doctrro.bs.beans.Booking;
import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.bs.beans.BookingStatus;
import com.innova.doctrro.bs.beans.SlotStatus;
import com.innova.doctrro.bs.dao.BookingDao;
import com.innova.doctrro.bs.dao.ReactiveBookingDao;
import com.innova.doctrro.bs.service.*;
import com.innova.doctrro.common.constants.PaymentStatus;
import com.innova.doctrro.common.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.innova.doctrro.bs.dto.BookingDto.BookingDtoRequest;
import static com.innova.doctrro.bs.dto.BookingDto.BookingDtoResponse;
import static com.innova.doctrro.bs.service.Converters.BookingConverter;
import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;

@Service
public class ReactiveBookingServiceImpl implements ReactiveBookingService {

    private final ReactiveBookingDao bookingDao;
    private final ReactiveBookingSlotService bookingSlotService;
    private final DoctorServiceClient doctorServiceClient;
    private final Map<String, String> lockedSlots = new ConcurrentHashMap<>();

    @Autowired
    public ReactiveBookingServiceImpl(ReactiveBookingDao bookingDao, ReactiveBookingSlotService bookingSlotService, DoctorServiceClient doctorServiceClient) {
        this.bookingDao = bookingDao;
        this.bookingSlotService = bookingSlotService;
        this.doctorServiceClient = doctorServiceClient;
    }

    @Override
    public Mono<Boolean> lockSlot(String slotId, String email) {
        String r = lockedSlots.putIfAbsent(slotId, email);
        System.out.println(email + " " + r + " " + slotId);
        if (r == null) return Mono.just(true);
        return Mono.just(email.equals(r));  //TODO - Replace with Redis call
    }

    @Override
    public Mono<BookingDtoResponse> create(BookingDtoRequest item) {
        Booking booking = BookingConverter.convert(item);
        String lockedBy = lockedSlots.getOrDefault(item.getSlotId(), null);  //TODO - Replace with Redis call

        if (!item.getBookedBy().getEmail().equals(lockedBy)) {
            return Mono.error(new InvalidInputException("You haven't locked the slot. If you had locked it, make sure its not expired"));
        } else {
            var now = LocalDateTime.now();
            return bookingSlotService.findById(item.getSlotId())
                    .flatMap(slot -> {
                        if (now.isAfter(slot.getStartTime())) {
                            return Mono.error(new InvalidInputException("You are trying to book a old slot."));
                        }

                        return Mono.just(slot);
                    }).flatMap(slot -> {
                        if (slot.getStatus() == SlotStatus.AVAILABLE) {
                            slot.setStatus(SlotStatus.LOCKED);
                            return bookingSlotService.update(slot.getId(), slot);
                        } else {
                            return Mono.error(new InvalidInputException("Selected slot is currently not available."));
                        }
                    }).flatMap(slot -> {
                        booking.setStatus(BookingStatus.INITIATED);
                        booking.getFacility().setName(slot.getFacilityName());
                        booking.getPractioner().setName(slot.getDoctorName());
                        booking.setCost(slot.getCharge());
                        lockedSlots.remove(slot.getId());  //TODO - Replace with Redis call
                        return bookingDao.create(booking);
                    }).map(BookingConverter::convert);
        }
    }

    @Override
    public Mono<BookingDtoResponse> findById(String s) {
        return bookingDao.findById(s)
                .map(BookingConverter::convert);
    }

    @Override
    public Flux<BookingDtoResponse> findAllByBookeUserEmail(String email) {
        return bookingDao.findAllByBookedUserEmail(email)
                .map(BookingConverter::convert);
    }

    @Override
    public Flux<BookingDtoResponse> findAllByPractitionerId(String regId) {
        return bookingDao.findAllByPractitionerRegId(regId)
                .map(BookingConverter::convert);
    }

    @Override
    public Flux<BookingDtoResponse> findAllByPractitionerEmail(String token) {
        return doctorServiceClient.find(token)
                .flatMapMany(doctor -> findAllByPractitionerId(doctor.getRegId()));
    }

    @Override
    public Flux<BookingDtoResponse> findAll() {
        return null;
    }

    @Override
    public Mono<BookingDtoResponse> update(String s, BookingDtoRequest item) {
        return null;
    }

    @Override
    public Mono<BookingDtoResponse> update(String bookingId, BookingStatus status) {
        return bookingDao.findById(bookingId)
                .flatMap(booking -> bookingSlotService.findById(booking.getSlotId()))
                .map(slot -> null);
//        Booking booking = bookingDao.findById(bookingId);
//        BookingSlot slot = bookingSlotService.findById(booking.getSlotId());
//
//        switch (status) {
//            case CONFIRMED:
//                if (slot.getStatus() == SlotStatus.LOCKED && booking.getStatus() == BookingStatus.INITIATED) {
//                    slot.setStatus(SlotStatus.BOOKED);
//                    bookingSlotService.update(slot.getId(), slot);
//
//                    if (slot.isAutoApproveEnabled()) {
//                        booking.setStatus(BookingStatus.CONFIRMED);
//                    } else {
//                        booking.setStatus(BookingStatus.PENDING_APPROVAL);
//                    }
//                    return BookingConverter.convert(bookingDao.update(booking.getId(), booking));
//                } else {
//                    return null;
//                }
//            case FAILED:
//                if (slot.getStatus() == SlotStatus.LOCKED && booking.getStatus() == BookingStatus.INITIATED) {
//                    slot.setStatus(SlotStatus.AVAILABLE);
//                    bookingSlotService.update(slot.getId(), slot);
//
//                    booking.setStatus(BookingStatus.FAILED);
//                    return BookingConverter.convert(bookingDao.update(booking.getId(), booking));
//                } else {
//                    return null;
//                }
//            case CANCELLED:
//                if (slot.getStatus() == SlotStatus.BOOKED && booking.getStatus() == BookingStatus.CONFIRMED) {
//                    slot.setStatus(SlotStatus.AVAILABLE);
//                    bookingSlotService.update(slot.getId(), slot);
//
//                    booking.setStatus(BookingStatus.CANCELLED);
//                    return BookingConverter.convert(bookingDao.update(booking.getId(), booking));
//                } else {
//                    return null;
//                }
//            default:
//                throw new UnsupportedOperationException("Given status update not supported.");
//        }
    }

    @Override
    public Mono<Void> update(String bookingId, PaymentStatus status) {
        return Mono.empty();
//        switch (status) {
//            case SUCCEDED:
//                update(bookingId, BookingStatus.CONFIRMED);
//                break;
//            case FAILED:
//                update(bookingId, BookingStatus.FAILED);
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public Mono<Void> remove(String s) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove(BookingDtoRequest item) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove() {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }
}
