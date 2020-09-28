package com.innova.doctrro.ss.controller;


import com.innova.doctrro.ss.dto.DoctorDtoResponse;
import com.innova.doctrro.ss.dto.FacilityDtoResponse;
import com.innova.doctrro.ss.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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

    @GetMapping("/doctor")
    public Flux<DoctorDtoResponse> search(@RequestParam(value = "lat") double latitude,
                                            @RequestParam(value = "lon") double longitude,
                                            @RequestParam(value = "radius") int radius,
                                            @RequestParam(value = "specialization", required = false) String specialization) {
        return searchService.search(latitude, longitude, radius, specialization);
    }

}
