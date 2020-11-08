package com.innova.doctrro.ss.controller;


import com.innova.doctrro.ss.dto.DoctorDtoResponse;
import com.innova.doctrro.ss.dto.FacilityDtoResponse;
import com.innova.doctrro.ss.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/search-service")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/facility")
    public Flux<FacilityDtoResponse> search(@RequestParam(value = "lat") double latitude,
                                            @RequestParam(value = "lon") double longitude,
                                            @RequestParam(value = "radius") int radius) {
        return searchService.search(latitude, longitude, radius);
    }

//    @GetMapping("/facility/{fid}/doctor/{regId}")
//    public Mono<FacilityDtoResponse> getAllSlots(@PathVariable String fid,
//                                                 @PathVariable String regId) {
//        return searchService.findAllBookingSlots(fid, regId);
//    }

    @GetMapping("/doctor")
    public Flux<DoctorDtoResponse> search(@RequestParam(value = "lat") double latitude,
                                            @RequestParam(value = "lon") double longitude,
                                            @RequestParam(value = "radius") int radius,
                                            @RequestParam(value = "specialization", required = false) String specialization) {
        return searchService.search(latitude, longitude, radius, specialization);
    }

}
