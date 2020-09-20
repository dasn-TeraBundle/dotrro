package com.innova.doctrro.docs.dao.impl;

import com.innova.doctrro.common.beans.Doctor;
import com.innova.doctrro.docs.dao.ReactiveDoctorDao;
import com.innova.doctrro.docs.dao.repository.ReactiveDoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        return doctorRepository.insert(item);
    }

    @Override
    public Mono<Doctor> findByEmail(String email) {
        return doctorRepository.findByEmailsContains(email);
    }

    @Override
    public Mono<Doctor> findById(String s) {
        return doctorRepository.findById(s);
    }

    @Override
    public Flux<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    @Override
    public Mono<Doctor> update(String s, Doctor item) {
        return doctorRepository.save(item);
    }

    @Override
    public Mono<Void> remove(String s) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove(Doctor item) {
        return doctorRepository.delete(item);
    }

    @Override
    public Mono<Void> remove() {
        return doctorRepository.deleteAll();
    }
}
