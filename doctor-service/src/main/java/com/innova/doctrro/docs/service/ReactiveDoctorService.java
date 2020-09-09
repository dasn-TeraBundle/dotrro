package com.innova.doctrro.docs.service;

import static com.innova.doctrro.common.dto.DoctorDto.*;
import com.innova.doctrro.common.service.ReactiveGenericService;
import reactor.core.publisher.Mono;

public interface ReactiveDoctorService extends ReactiveGenericService<DoctorDtoRequest, DoctorDtoResponse, String> {

    Mono<DoctorDtoResponse> findByEmail(String email);

}
