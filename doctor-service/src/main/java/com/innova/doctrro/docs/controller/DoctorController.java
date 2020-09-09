package com.innova.doctrro.docs.controller;


import com.innova.doctrro.docs.service.ReactiveDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static com.innova.doctrro.common.dto.DoctorDto.DoctorDtoRequest;
import static com.innova.doctrro.common.dto.DoctorDto.DoctorDtoResponse;

@RestController
@RequestMapping("/doctor-service/doctors")
public class DoctorController {

    private final ReactiveDoctorService reactiveDoctorService;

    @Autowired
    public DoctorController(ReactiveDoctorService reactiveDoctorService) {
        this.reactiveDoctorService = reactiveDoctorService;
    }

    @PostMapping("/")
    public Mono<DoctorDtoResponse> create(Mono<BearerTokenAuthentication> auth,
                                          @RequestBody @Valid DoctorDtoRequest request) {
        return auth.map(BearerTokenAuthentication::getTokenAttributes)
                .map(details -> {
                    if (request.getEmail() == null) {
                        request.setEmail(details.get("email").toString());
                        request.setName(details.get("name").toString());
                    }
                    return request;
                }).flatMap(reactiveDoctorService::create);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_DOCTOR')")
    @GetMapping("/me")
    public Mono<DoctorDtoResponse> find(Mono<BearerTokenAuthentication> auth) {
        return auth.map(BearerTokenAuthentication::getTokenAttributes)
                .flatMap(details -> {
                    return reactiveDoctorService.findByEmail(details.get("email").toString());
                });
    }

    @GetMapping("/reg-id/{regId}")
    public Mono<DoctorDtoResponse> find(@PathVariable String regId) {
        return reactiveDoctorService.findById(regId);
    }

    @GetMapping("/")
    public Flux<DoctorDtoResponse> find() {
        return reactiveDoctorService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_DOCTOR')")
    @PatchMapping("/")
    public Mono<DoctorDtoResponse> update(Mono<BearerTokenAuthentication> auth,
                                          @RequestBody DoctorDtoRequest request) {
        return auth.map(BearerTokenAuthentication::getTokenAttributes)
                .flatMap(details -> reactiveDoctorService.update(details.get("email").toString(), request));
    }
}
