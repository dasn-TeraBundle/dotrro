package com.innova.doctrro.docs.service;

import static com.innova.doctrro.common.dto.DoctorRatingDto.*;
import com.innova.doctrro.common.service.ReactiveGenericService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveDoctorRatingService extends ReactiveGenericService<DoctorRatingDtoRequest, DoctorRatingDtoResponse, String> {

    Flux<DoctorRatingDtoResponse> findAllByDoctorRegId(String regId);
    Mono<Double> findAverageRatingByDoctorRegId(String regId);
    Flux<DoctorRatingDtoResponse> findAllByRatedByEmail(String email);

}
