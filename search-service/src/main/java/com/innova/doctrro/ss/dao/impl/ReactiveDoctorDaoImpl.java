package com.innova.doctrro.ss.dao.impl;

import com.innova.doctrro.common.beans.Doctor;
import com.innova.doctrro.ss.dao.ReactiveDoctorDao;
import com.innova.doctrro.ss.dao.repository.ReactiveDoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;


@Component
public class ReactiveDoctorDaoImpl implements ReactiveDoctorDao {

    private final ReactiveDoctorRepository doctorRepository;

    @Autowired
    public ReactiveDoctorDaoImpl(ReactiveDoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Mono<Doctor> create(Doctor item) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Doctor> findById(String s) {
        return doctorRepository.findById(s);
    }

    @Override
    public Flux<Doctor> findAllByRegIdIn(List<String> regIds) {
        return doctorRepository.findAllByRegIdIn(regIds);
    }

    @Override
    public Flux<Doctor> findAllByRegIdInAndSpeciality(List<String> regIds, String speciality) {
        return doctorRepository.findAllByRegIdInAndAbout_Speciality(regIds, speciality);
    }

    @Override
    public Flux<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    @Override
    public Mono<Doctor> update(String s, Doctor item) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove(Doctor item) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove() {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }
}
