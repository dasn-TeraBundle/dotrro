package com.innova.doctrro.ps.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.innova.doctrro.common.dto.KafkaMessage;
import com.innova.doctrro.common.service.KafkaService;
import com.innova.doctrro.ps.beans.Patient;
import com.innova.doctrro.ps.dao.PatientDao;
import com.innova.doctrro.ps.exception.PatientException;
import com.innova.doctrro.ps.exception.PatientNotFoundException;
import com.innova.doctrro.ps.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;
import static com.innova.doctrro.ps.dto.PatientDto.*;


@Service
public class PatientServiceImpl implements PatientService {

    private static final String TOPIC = "users_create";
    private final PatientDao patientDao;
    private final KafkaService kafkaService;

    @Autowired
    public PatientServiceImpl(PatientDao patientDao, KafkaService kafkaService) {
        this.patientDao = patientDao;
        this.kafkaService = kafkaService;
    }

    @Override
    public PatientDtoResponse create(PatientDtoRequest item) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }

    public PatientDtoResponse create(Map<String, String> details, PatientDtoRequest item) {
        Patient patient = convert(item);
        patient.setEmail(details.get("email"));
        patient.setName(details.get("name"));

        var resp = convert(patientDao.create(patient));
        var kafkaMsg = new KafkaMessage(resp.getEmail(), "C", "patients");
        try {
            kafkaService.send(TOPIC, kafkaMsg);
        } catch (JsonProcessingException e) {
            remove(resp.getEmail());
            throw new PatientException("Patient Registration Failed. Please report to app owner");
        }

        return resp;
    }

    @Override
    public PatientDtoResponse findById(String s) {
        Patient patient = patientDao.findById(s);
        if (patient == null) {
            throw new PatientNotFoundException();
        }

        return convert(patient);
    }

    @Override
    public List<PatientDtoResponse> findAll() {
        return convert(patientDao.findAll());
    }

    @Override
    public PatientDtoResponse update(String s, PatientDtoRequest item) {
        Patient patient = patientDao.findById(s);
        if (patient == null) {
            throw new PatientNotFoundException();
        }
        Patient patient1 = convert(item);
        patient1.setEmail(s);
        patient1.setName(patient.getName());

        return convert(patientDao.update(s, patient1));
    }

    @Override
    public void remove(String s) {
        Patient patient = patientDao.findById(s);
        if (patient == null) {
            throw new PatientNotFoundException();
        }

        patientDao.remove(patient);
    }

    @Override
    public void remove() {
        patientDao.remove();
    }
}
