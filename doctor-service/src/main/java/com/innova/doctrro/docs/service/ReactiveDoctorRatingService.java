package com.innova.doctrro.docs.service;

import static com.innova.doctrro.common.dto.DoctorRatingDto.*;
import com.innova.doctrro.common.service.ReactiveGenericService;
import com.innova.doctrro.docs.beans.DoctorRating;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveDoctorRatingService extends ReactiveGenericService<DoctorRatingDtoRequest, DoctorRatingDtoResponse, String> {

    Flux<DoctorRatingDtoResponse> findAllByDoctorRegId(String regId);
    Mono<Double> findAllRatingByDoctor_RegId(String regId);
    Flux<DoctorRatingDtoResponse> findAllByRatedByEmail(String email);

}
