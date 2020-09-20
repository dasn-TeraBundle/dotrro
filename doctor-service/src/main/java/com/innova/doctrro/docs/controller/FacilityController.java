package com.innova.doctrro.docs.controller;


import static com.innova.doctrro.common.dto.FacilityDto.*;
import com.innova.doctrro.docs.service.ReactiveFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/doctor-service/facility")
public class FacilityController {

    private final ReactiveFacilityService facilityService;

    @Autowired
    public FacilityController(ReactiveFacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FacilityDtoResponse> create(Mono<BearerTokenAuthentication> auth,
                                            @RequestBody @Valid FacilityDtoRequest request) {
        return auth.map(BearerTokenAuthentication::getTokenAttributes)
                .flatMap(details -> {
                    request.setAdmin(new Admin(details.get("email").toString(), details.get("name").toString()));

                    return facilityService.create(request);
                });
    }

    @GetMapping("/{id}")
    public Mono<FacilityDtoResponse> find(@PathVariable String id) {
        return facilityService.findById(id);
    }

    @GetMapping("/")
    public Flux<FacilityDtoResponse> find() {
        return facilityService.findAll();
    }

    @GetMapping("/me")
    public Flux<FacilityDtoResponse> find(Mono<BearerTokenAuthentication> auth) {
        return auth.map(BearerTokenAuthentication::getTokenAttributes)
                .flatMapMany(details -> facilityService.findAllByAdminEmail(details.get("email").toString()));
    }
}
