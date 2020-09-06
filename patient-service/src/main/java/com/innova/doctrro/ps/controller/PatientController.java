package com.innova.doctrro.ps.controller;


import com.innova.doctrro.ps.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.innova.doctrro.ps.dto.PatientDto.PatientDtoRequest;
import static com.innova.doctrro.ps.dto.PatientDto.PatientDtoResponse;

@RestController
@RequestMapping("/patient-service")
public class PatientController {

    private static final String EMAIL_PARAM = "email";
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PreAuthorize("hasAuthority('ROLE_PATIENT')")
    @GetMapping("/")
    public Mono<PatientDtoResponse> getPatient(BearerTokenAuthentication auth) {
        Map<String, Object> details = auth.getTokenAttributes();

        return Mono.just(patientService.findById(details.get(EMAIL_PARAM).toString()));
    }

    @GetMapping("/{email}")
    public PatientDtoResponse getPatient(@PathVariable String email) {
        return patientService.findById(email);
    }

    @PostMapping("/")
    public Mono<PatientDtoResponse> register(BearerTokenAuthentication auth,
                                             @RequestBody @Valid PatientDtoRequest request) {
        Map<String, Object> details = auth.getTokenAttributes();
        Map<String, String> de = new HashMap<>();
        details.forEach((k, v) -> de.put(k, v.toString()));

        return Mono.just(patientService.create(de, request));
    }

    @PreAuthorize("hasAuthority('ROLE_PATIENT')")
    @PutMapping("/")
    public Mono<PatientDtoResponse> update(BearerTokenAuthentication auth,
                                           @RequestBody @Valid PatientDtoRequest request) {
        Map<String, Object> details = auth.getTokenAttributes();

        return Mono.just(patientService.update(details.get(EMAIL_PARAM).toString(), request));
    }

    @PreAuthorize("hasAuthority('ROLE_PATIENT')")
    @DeleteMapping("/")
    public Mono<Void> delete(BearerTokenAuthentication auth) {
        Map<String, Object> details = auth.getTokenAttributes();

        patientService.remove(details.get(EMAIL_PARAM).toString());
        return Mono.empty();
    }
}
