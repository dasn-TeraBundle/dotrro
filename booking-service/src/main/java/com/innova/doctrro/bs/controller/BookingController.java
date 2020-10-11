package com.innova.doctrro.bs.controller;


import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.bs.beans.SlotStatus;
import com.innova.doctrro.bs.service.BookingService;
import com.innova.doctrro.bs.service.BookingSlotService;
import com.innova.doctrro.bs.service.SearchServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import static com.innova.doctrro.bs.dto.BookingDto.*;

@RestController
@RequestMapping("/booking-service")
public class BookingController {

    private final BookingService bookingService;
    private final BookingSlotService bookingSlotService;

    @Autowired
    public BookingController(BookingService bookingService, BookingSlotService bookingSlotService) {
        this.bookingService = bookingService;
        this.bookingSlotService = bookingSlotService;
    }

    @PostMapping("/")
    public BookingDtoResponse book(BearerTokenAuthentication auth,
                                   @RequestBody @Valid BookingDtoRequest request) {
        Map<String, Object> details = auth.getTokenAttributes();
        request.setBookedBy(new User(details.get("email").toString(), details.get("name").toString()));

        return bookingService.create(request);
    }

    @PostMapping("/lock/{slotId}")
    public boolean lock(BearerTokenAuthentication auth,
                        @PathVariable String slotId) {
        Map<String, Object> details = auth.getTokenAttributes();

        return bookingService.lockSlot(slotId, details.get("email").toString());
    }

    @GetMapping("/")
    public void createSlots() {
        bookingSlotService.create(new ArrayList<>());
    }
}
