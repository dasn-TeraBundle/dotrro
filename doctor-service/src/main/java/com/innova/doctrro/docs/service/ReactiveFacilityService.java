package com.innova.doctrro.docs.service;

import static com.innova.doctrro.common.dto.FacilityDto.*;
import com.innova.doctrro.common.service.ReactiveGenericService;
import reactor.core.publisher.Flux;

public interface ReactiveFacilityService extends ReactiveGenericService<FacilityDtoRequest, FacilityDtoResponse, String> {

    Flux<FacilityDtoResponse> findAllByAdminEmail(String email);

}
