package com.innova.doctrro.bs.controller;


import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.bs.service.ReactiveBookingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/booking-service/slot")
public class BookingSlotController {

    private final ReactiveBookingSlotService bookingSlotService;

    @Autowired
    public BookingSlotController(ReactiveBookingSlotService bookingSlotService) {
        this.bookingSlotService = bookingSlotService;
    }

        @GetMapping("/facility/{fid}/doctor/{regId}")
    public Flux<BookingSlot> getAllSlots(@PathVariable String fid,
                                         @PathVariable String regId) {
        return bookingSlotService.findAllFutureSLotsByFacilityIdAndDoctorId(fid, regId);
    }

}