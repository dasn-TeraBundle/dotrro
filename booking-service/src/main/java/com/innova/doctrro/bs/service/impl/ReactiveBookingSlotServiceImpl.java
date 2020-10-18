package com.innova.doctrro.bs.service.impl;

import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.bs.beans.SlotStatus;
import com.innova.doctrro.bs.dao.BookingSlotDao;
import com.innova.doctrro.bs.dao.ReactiveBookingSlotDao;
import com.innova.doctrro.bs.service.BookingSlotService;
import com.innova.doctrro.bs.service.ReactiveBookingSlotService;
import com.innova.doctrro.bs.service.SearchServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;


@Service
public class ReactiveBookingSlotServiceImpl implements ReactiveBookingSlotService {

    private final ReactiveBookingSlotDao bookingSlotDao;
    private final SearchServiceClient searchServiceClient;

    @Autowired
    public ReactiveBookingSlotServiceImpl(ReactiveBookingSlotDao bookingSlotDao, SearchServiceClient searchServiceClient) {
        this.bookingSlotDao = bookingSlotDao;
        this.searchServiceClient = searchServiceClient;
    }

    @Override
    public Flux<BookingSlot> create(List<BookingSlot> items) {
        return searchServiceClient.getAllSlots("5f68e23d217eae3b49b81a06", "00001")
                .flatMapMany(resp -> {
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
                    return bookingSlotDao.create(slots);
                });

    }

    @Override
    public Mono<BookingSlot> create(BookingSlot item) {
        return bookingSlotDao.create(item);
    }

    @Override
    public Mono<BookingSlot> findById(String s) {
        return bookingSlotDao.findById(s)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException("Invalid slot id"))))
                .map(slot -> slot);
    }

    @Override
    public Flux<BookingSlot> findAll() {
        return null;
    }

    @Override
    public Mono<BookingSlot> update(String s, BookingSlot item) {
        return bookingSlotDao.update(s, item);
    }

    @Override
    public Mono<Void> remove(String s) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove(BookingSlot item) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove() {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }
}
