package com.innova.doctrro.bs.service;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import static com.innova.doctrro.common.dto.DoctorDto.DoctorDtoResponse;

@ReactiveFeignClient("doctor-service")
public interface DoctorServiceClient {

    @GetMapping("/doctor-service/doctors/me")
    Mono<DoctorDtoResponse> find(@RequestHeader(value = "Authorization") String bearerToken);
}
