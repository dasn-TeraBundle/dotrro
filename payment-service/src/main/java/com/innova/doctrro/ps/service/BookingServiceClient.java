package com.innova.doctrro.ps.service;


import com.innova.doctrro.ps.dto.BookingDtoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient("booking-service")
public interface BookingServiceClient {

    @GetMapping(value = "/booking-service/booking/{id}")
    Mono<BookingDtoResponse> find(@PathVariable(value = "id") String id);
}