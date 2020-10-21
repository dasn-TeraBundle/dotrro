package com.innova.doctrro.bs.service;


import com.innova.doctrro.bs.dto.FacilityDtoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient("search-service")
public interface SearchServiceClient {

    @GetMapping("/search-service/facility/{fid}/doctor/{regId}")
    Mono<FacilityDtoResponse> getAllSlots(@PathVariable(value = "fid") String fid,
                                          @PathVariable(value = "regId") String regId);
}
