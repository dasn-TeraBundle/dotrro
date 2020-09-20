package com.innova.doctrro.docs.dao.impl;

import com.innova.doctrro.common.beans.Doctor;
import com.innova.doctrro.docs.dao.DoctorDao;
import com.innova.doctrro.docs.dao.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;


@Component
public class DoctorDaoImpl implements DoctorDao {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorDaoImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor create(Doctor doctor) {
        return doctorRepository.insert(doctor);
    }

    @Override
    public Doctor findById(String regId) {
        return doctorRepository.findById(regId).orElse(null);
    }

    @Override
    public Doctor findByEmail(String email) {
        return doctorRepository.findByEmailsContains(email);
    }

    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor update(String regId, Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public void remove(String s) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }

    @Override
    public void remove(Doctor doctor) {
        doctorRepository.delete(doctor);
    }

    @Override
    public void remove() {
        doctorRepository.deleteAll();
    }
}
