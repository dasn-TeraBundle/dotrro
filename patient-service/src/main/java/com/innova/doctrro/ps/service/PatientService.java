package com.innova.doctrro.ps.service;

import com.innova.doctrro.common.service.GenericService;

import java.util.Map;

import static com.innova.doctrro.ps.dto.PatientDto.PatientDtoRequest;
import static com.innova.doctrro.ps.dto.PatientDto.PatientDtoResponse;

public interface PatientService extends GenericService<PatientDtoRequest, PatientDtoResponse, String> {
    PatientDtoResponse create(Map<String, String> details, PatientDtoRequest item);
}
