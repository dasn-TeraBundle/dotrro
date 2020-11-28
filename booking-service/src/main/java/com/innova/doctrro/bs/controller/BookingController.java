package com.innova.doctrro.bs.controller;


import com.innova.doctrro.bs.service.BookingService;
import com.innova.doctrro.bs.service.ReactiveBookingService;
import static com.innova.doctrro.common.constants.AuthConstants.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.innova.doctrro.bs.dto.BookingDto.*;

@RestController
@RequestMapping("/booking-service")
public class BookingController {

    private final BookingService bookingService;
    private final ReactiveBookingService reactiveBookingService;

    @Autowired
    public BookingController(BookingService bookingService, ReactiveBookingService reactiveBookingService) {
        this.bookingService = bookingService;
        this.reactiveBookingService = reactiveBookingService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDtoResponse book(BearerTokenAuthentication auth,
                                   @RequestBody @Valid BookingDtoRequest request) {
        Map<String, Object> details = auth.getTokenAttributes();
        request.setBookedBy(new User(details.get(KEY_EMAIL).toString(), details.get("name").toString()));

        return bookingService.create(request);
    }

    @PostMapping("/lock/{slotId}")
    public boolean lock(BearerTokenAuthentication auth,
                        @PathVariable String slotId) {
        Map<String, Object> details = auth.getTokenAttributes();

        return bookingService.lockSlot(slotId, details.get(KEY_EMAIL).toString());
    }

    @GetMapping("/booking/{id}")
    public Mono<BookingDtoResponse> find(@PathVariable String id) {
        return reactiveBookingService.findById(id);
    }

    @GetMapping("/patients/me")
    public List<BookingDtoResponse> findAllBookingsByPatient(BearerTokenAuthentication auth) {
        Map<String, Object> details = auth.getTokenAttributes();
        String email = details.get(KEY_EMAIL).toString();

        return bookingService.findAllByBookeUserEmail(email);
    }

    @GetMapping("/doctors/me")
    public List<BookingDtoResponse> findAllBookingsForDoctor(BearerTokenAuthentication auth) {
        return bookingService.findAllByPractitionerEmail(KEY_BEARER + " " + auth.getToken().getTokenValue());
    }

    @GetMapping("/v2/patients/me")
    public Flux<BookingDtoResponse> findAllBookingsByPatient(Mono<BearerTokenAuthentication> auth) {
        return auth.map(BearerTokenAuthentication::getTokenAttributes)
                .map(details -> details.get(KEY_EMAIL).toString())
                .flatMapMany(reactiveBookingService::findAllByBookeUserEmail);
    }

    @GetMapping(value = "/v2/doctors/me", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<BookingDtoResponse> findAllBookingsForDoctor(Mono<BearerTokenAuthentication> auth) {
        return auth.map(BearerTokenAuthentication::getToken)
                .map(t -> KEY_BEARER + " " + t.getTokenValue())
                .flatMapMany(reactiveBookingService::findAllByPractitionerEmail);
    }

    @PatchMapping("/cancel/patients/{bookingId}")
    public BookingDtoResponse cancelByPatient(BearerTokenAuthentication auth,
                                             @PathVariable String bookingId) {
        Map<String, Object> details = auth.getTokenAttributes();
        String email = details.get(KEY_EMAIL).toString();

        return bookingService.cancelByPatient(bookingId, email);
    }

    @PatchMapping("/cancel/doctors/{bookingId}")
    public BookingDtoResponse cancelByDoctor(BearerTokenAuthentication auth,
                                            @PathVariable String bookingId) {
        return bookingService.cancelByDoctor(bookingId, KEY_BEARER + " " + auth.getToken().getTokenValue());
    }

}
