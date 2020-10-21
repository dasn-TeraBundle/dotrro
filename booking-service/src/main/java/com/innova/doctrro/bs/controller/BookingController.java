package com.innova.doctrro.bs.controller;


import com.innova.doctrro.bs.service.BookingService;
import com.innova.doctrro.bs.service.BookingSlotService;
import com.innova.doctrro.bs.service.ReactiveBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.innova.doctrro.bs.dto.BookingDto.*;

@RestController
@RequestMapping("/booking-service")
public class BookingController {

    private final BookingService bookingService;
    private final ReactiveBookingService reactiveBookingService;
//    private final BookingSlotService bookingSlotService;

    @Autowired
    public BookingController(BookingService bookingService, ReactiveBookingService reactiveBookingService/*, BookingSlotService bookingSlotService*/) {
        this.bookingService = bookingService;
        this.reactiveBookingService = reactiveBookingService;
//        this.bookingSlotService = bookingSlotService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
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

//    @GetMapping("/")
//    @Deprecated
//    public void createSlots() {
//        bookingSlotService.create(new ArrayList<>());
//    }

    @GetMapping("/patients/me")
    public List<BookingDtoResponse> findMyBookings_Patient(BearerTokenAuthentication auth) {
        Map<String, Object> details = auth.getTokenAttributes();
        String email = details.get("email").toString();

        return bookingService.findAllByBookeUserEmail(email);
    }

    @GetMapping("/doctors/me")
    public List<BookingDtoResponse> findMyBookings_Doctor(BearerTokenAuthentication auth) {
        return bookingService.findAllByPractitionerEmail("Bearer " + auth.getToken().getTokenValue());
    }

    @GetMapping("/v2/patients/me")
    public Flux<BookingDtoResponse> findMyBookings_Patient(Mono<BearerTokenAuthentication> auth) {
        return auth.map(BearerTokenAuthentication::getTokenAttributes)
                .map(details -> details.get("email").toString())
                .flatMapMany(reactiveBookingService::findAllByBookeUserEmail);
    }

    @GetMapping(value = "/v2/doctors/me", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<BookingDtoResponse> findMyBookings_Doctor(Mono<BearerTokenAuthentication> auth) {
        return auth.map(BearerTokenAuthentication::getToken)
                .map(t -> "Bearer " + t.getTokenValue())
                .flatMapMany(reactiveBookingService::findAllByPractitionerEmail);
    }

    @PatchMapping("/cancel/patients/{bookingId}")
    public BookingDtoResponse cancel_Patient(BearerTokenAuthentication auth,
                                                           @PathVariable String bookingId) {
        Map<String, Object> details = auth.getTokenAttributes();
        String email = details.get("email").toString();

        return bookingService.cancelByPatient(bookingId, email);
    }

    @PatchMapping("/cancel/doctors/{bookingId}")
    public BookingDtoResponse cancel_Doctor(BearerTokenAuthentication auth,
                                                          @PathVariable String bookingId) {
        return bookingService.cancelByDoctor(bookingId, "Bearer " + auth.getToken().getTokenValue());
    }

}
