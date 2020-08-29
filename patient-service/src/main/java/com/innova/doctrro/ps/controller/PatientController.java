package com.innova.doctrro.ps.controller;


import com.innova.doctrro.ps.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.innova.doctrro.ps.dto.PatientDto.PatientDtoRequest;
import static com.innova.doctrro.ps.dto.PatientDto.PatientDtoResponse;

@RestController
public class PatientController {

    private static final String EMAIL_PARAM = "email";
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PreAuthorize("hasAuthority('ROLE_PATIENT')")
    @GetMapping("/")
    public PatientDtoResponse getPatient(OAuth2Authentication auth) {
        Map<?, ?> details = (Map<?, ?>) auth.getUserAuthentication().getDetails();

        return patientService.findById(details.get(EMAIL_PARAM).toString());
    }

    @GetMapping("/{email}")
    public PatientDtoResponse getPatient(@PathVariable String email) {
        return patientService.findById(email);
    }

    @PostMapping("/")
    public PatientDtoResponse register(OAuth2Authentication auth,
                                       @RequestBody @Valid PatientDtoRequest request) {
        Map<?, ?> details = (Map<?, ?>) auth.getUserAuthentication().getDetails();
        Map<String, String> de = new HashMap<>();
        details.forEach((k, v) -> de.put(k.toString(), v.toString()));

        return patientService.create(de, request);
    }

    @PreAuthorize("hasAuthority('ROLE_PATIENT')")
    @PutMapping("/")
    public PatientDtoResponse update(OAuth2Authentication auth,
                                     @RequestBody @Valid PatientDtoRequest request) {
        Map<?, ?> details = (Map<?, ?>) auth.getUserAuthentication().getDetails();

        return patientService.update(details.get(EMAIL_PARAM).toString(), request);
    }

    @PreAuthorize("hasAuthority('ROLE_PATIENT')")
    @DeleteMapping("/")
    public void delete(OAuth2Authentication auth) {
        Map<?, ?> details = (Map<?, ?>) auth.getUserAuthentication().getDetails();

        patientService.remove(details.get(EMAIL_PARAM).toString());
    }
}
