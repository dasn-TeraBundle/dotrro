package com.innova.doctrro.ss.service;

import com.innova.doctrro.ss.dto.DoctorDtoResponse;
import com.innova.doctrro.ss.dto.FacilityDtoResponse;
import reactor.core.publisher.Flux;

public interface SearchService {

    Flux<FacilityDtoResponse> search(double latitude, double longitude, int radius);
    Flux<DoctorDtoResponse> search(double latitude, double longitude, int radius, String speciality);
}
