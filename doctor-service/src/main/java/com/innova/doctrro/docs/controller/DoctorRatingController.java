package com.innova.doctrro.docs.controller;


import com.innova.doctrro.docs.service.ReactiveDoctorRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static com.innova.doctrro.common.dto.DoctorRatingDto.DoctorRatingDtoRequest;
import static com.innova.doctrro.common.dto.DoctorRatingDto.DoctorRatingDtoResponse;

@RestController
@RequestMapping("/doctor-service/doc-ratings")
public class DoctorRatingController {

    private final ReactiveDoctorRatingService doctorRatingService;

    @Autowired
    public DoctorRatingController(ReactiveDoctorRatingService doctorRatingService) {
        this.doctorRatingService = doctorRatingService;
    }

    @PostMapping("/")
    public Mono<DoctorRatingDtoResponse> create(Mono<BearerTokenAuthentication> auth,
                                                @RequestBody @Valid DoctorRatingDtoRequest request) {
        return auth.map(BearerTokenAuthentication::getTokenAttributes)
                .map(details -> {
                    request.setRatedByEmail(details.get("email").toString());
                    request.setRatedByName(details.get("name").toString());
                    return request;
                }).flatMap(doctorRatingService::create);
    }

    @GetMapping("/doc/{regId}")
    public Flux<DoctorRatingDtoResponse> findAllByDoctorRegId(@PathVariable String regId) {
        return doctorRatingService.findAllByDoctorRegId(regId);
    }

    @GetMapping("/doc-avg/{regId}")
    public Mono<Double> findAverageRatingByDoctorRegId(@PathVariable String regId) {
        return doctorRatingService.findAverageRatingByDoctorRegId(regId);
    }

    @GetMapping("/me")
    public Flux<DoctorRatingDtoResponse> findAllByRatedByEmail(Mono<BearerTokenAuthentication> auth) {
        return auth.map(BearerTokenAuthentication::getTokenAttributes)
                .flatMapMany(details -> doctorRatingService.findAllByRatedByEmail(details.get("email").toString()));
    }
}
