package com.innova.doctrro.bs.service.impl;

import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.bs.beans.SlotStatus;
import com.innova.doctrro.bs.dao.BookingSlotDao;
import com.innova.doctrro.bs.exception.BookingSlotDBExceptionFactory;
import com.innova.doctrro.bs.service.BookingSlotService;
import com.innova.doctrro.bs.service.SearchServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.innova.doctrro.common.constants.DBExceptionType.DATA_NOT_FOUND;
import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;


@Service
public class BookingSlotServiceImpl implements BookingSlotService {

    private final BookingSlotDao bookingSlotDao;
    private final SearchServiceClient searchServiceClient;

    @Autowired
    public BookingSlotServiceImpl(BookingSlotDao bookingSlotDao, SearchServiceClient searchServiceClient) {
        this.bookingSlotDao = bookingSlotDao;
        this.searchServiceClient = searchServiceClient;
    }

    @Override
    public List<BookingSlot> create(List<BookingSlot> items) {
        searchServiceClient.getAllSlots("5f68e23d217eae3b49b81a06", "00001")
                .subscribe(resp -> {
                    var doctor = resp.getPractitioners().get(0);
                    var slots = doctor.getSlots().stream()
                            .map(slot -> {
                                var slt = new BookingSlot();
                                slt.setFacilityId(resp.getId());
                                slt.setFacilityName(resp.getName());
                                slt.setDoctorId(doctor.getRegId());
                                slt.setDoctorName(doctor.getName());
                                slt.setStartTime(slot.getStartTime());
                                slt.setEndTime(slot.getEndTime());
                                slt.setCharge(slot.getFee());
                                slt.setAutoApproveEnabled(slot.isAutoApproveEnabled());
                                slt.setStatus(SlotStatus.AVAILABLE);
                                slt.setBookingEnabled(true);

                                return slt;
                            }).collect(Collectors.toList());
                    bookingSlotDao.create(slots);
                }, err -> err.printStackTrace());

        return null;
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
