package com.innova.doctrro.bs.service.impl;

import com.innova.doctrro.bs.beans.Booking;
import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.bs.beans.BookingStatus;
import com.innova.doctrro.bs.beans.SlotStatus;
import com.innova.doctrro.bs.dao.BookingDao;
import com.innova.doctrro.bs.service.BookingService;
import com.innova.doctrro.bs.service.BookingSlotService;
import com.innova.doctrro.common.constants.PaymentStatus;
import com.innova.doctrro.common.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.innova.doctrro.bs.dto.BookingDto.BookingDtoRequest;
import static com.innova.doctrro.bs.dto.BookingDto.BookingDtoResponse;
import static com.innova.doctrro.bs.service.Converters.BookingConverter;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;
    private final BookingSlotService bookingSlotService;
    private final Map<String, String> lockedSlots = new ConcurrentHashMap<>();

    @Autowired
    public BookingServiceImpl(BookingDao bookingDao, BookingSlotService bookingSlotService) {
        this.bookingDao = bookingDao;
        this.bookingSlotService = bookingSlotService;
    }

    @Override
    public boolean lockSlot(String slotId, String email) {
        String r = lockedSlots.putIfAbsent(slotId, email);
        System.out.println(email + " " + r + " " + slotId);
        if (r == null) return true;
        return email.equals(r);  //TODO - Replace with Redis call
    }

    @Override
    public BookingDtoResponse create(BookingDtoRequest item) {
        Booking booking = BookingConverter.convert(item);
        String lockedBy = lockedSlots.getOrDefault(item.getSlotId(), null);  //TODO - Replace with Redis call

        if (!item.getBookedBy().getEmail().equals(lockedBy)) {
            throw new InvalidInputException("You haven't locked the slot. If you had locked it, make sure its not expired");
        } else {
            var now = LocalDateTime.now();
            BookingSlot slot = bookingSlotService.findById(item.getSlotId());
            if (now.isAfter(slot.getStartTime())) {
                throw new InvalidInputException("You are trying to book a old slot.");
            }
            if (slot.getStatus() == SlotStatus.AVAILABLE) {
                slot.setStatus(SlotStatus.LOCKED);
                bookingSlotService.update(slot.getId(), slot);

                booking.setStatus(BookingStatus.INITIATED);
                booking.getFacility().setName(slot.getFacilityName());
                booking.getPractioner().setName(slot.getDoctorName());
                booking.setCost(slot.getCharge());
                lockedSlots.remove(slot.getId());  //TODO - Replace with Redis call
                return BookingConverter.convert(bookingDao.create(booking));
            } else {
                throw new InvalidInputException("Selected slot is currently not available.");
            }
        }
    }

    @Override
    public BookingDtoResponse findById(String s) {
        return null;
    }

    @Override
    public List<BookingDtoResponse> findAll() {
        return null;
    }

    @Override
    public BookingDtoResponse update(String s, BookingDtoRequest item) {
        return null;
    }

    @Override
    public BookingDtoResponse update(String bookingId, BookingStatus status) {
        Booking booking = bookingDao.findById(bookingId);
        BookingSlot slot = bookingSlotService.findById(booking.getSlotId());

        switch (status) {
            case CONFIRMED:
                if (slot.getStatus() == SlotStatus.LOCKED && booking.getStatus() == BookingStatus.INITIATED) {
                    slot.setStatus(SlotStatus.BOOKED);
                    bookingSlotService.update(slot.getId(), slot);

                    if (slot.isAutoApproveEnabled()) {
                        booking.setStatus(BookingStatus.CONFIRMED);
                    } else {
                        booking.setStatus(BookingStatus.PENDING_APPROVAL);
                    }
                    return BookingConverter.convert(bookingDao.update(booking.getId(), booking));
                } else {
                    return null;
                }
            case FAILED:
                if (slot.getStatus() == SlotStatus.LOCKED && booking.getStatus() == BookingStatus.INITIATED) {
                    slot.setStatus(SlotStatus.AVAILABLE);
                    bookingSlotService.update(slot.getId(), slot);

                    booking.setStatus(BookingStatus.FAILED);
                    return BookingConverter.convert(bookingDao.update(booking.getId(), booking));
                } else {
                    return null;
                }
            case CANCELLED:
                if (slot.getStatus() == SlotStatus.BOOKED && booking.getStatus() == BookingStatus.CONFIRMED) {
                    slot.setStatus(SlotStatus.AVAILABLE);
                    bookingSlotService.update(slot.getId(), slot);

                    booking.setStatus(BookingStatus.CANCELLED);
                    return BookingConverter.convert(bookingDao.update(booking.getId(), booking));
                } else {
                    return null;
                }
            default:
                throw new UnsupportedOperationException("Given status update not supported.");
        }
    }

    @Override
    public void update(String bookingId, PaymentStatus status) {
        switch (status) {
            case SUCCEDED:
                update(bookingId, BookingStatus.CONFIRMED);
                break;
            case FAILED:
                update(bookingId, BookingStatus.FAILED);
                break;
            default:
                break;
        }
    }

    @Override
    public void remove(String s) {

    }

    @Override
    public void remove(BookingDtoRequest item) {

    }

    @Override
    public void remove() {

    }
}
