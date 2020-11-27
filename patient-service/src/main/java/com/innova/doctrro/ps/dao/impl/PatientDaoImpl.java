package com.innova.doctrro.ps.dao.impl;

import com.innova.doctrro.ps.beans.Patient;
import com.innova.doctrro.ps.dao.PatientDao;
import com.innova.doctrro.ps.dao.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PatientDaoImpl implements PatientDao {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientDaoImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    @CacheEvict(cacheNames = "patients", key = "'ALL_PATIENTS'")
    public Patient create(Patient item) {
        return patientRepository.insert(item);
    }

    @Override
    @Cacheable(cacheNames = "patients", key = "#email")
    public Patient findById(String email) {
        return patientRepository.findById(email).orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "patients", key = "'ALL_PATIENTS'")
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    @Caching(put = {
            @CachePut(cacheNames = "patients", key = "#email")
    }, evict = {
            @CacheEvict(cacheNames = "patients", key = "'ALL_PATIENTS'")
    })
    public Patient update(String email, Patient item) {
        return patientRepository.save(item);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "patients", key = "#item.email"),
            @CacheEvict(cacheNames = "patients", key = "'ALL_PATIENTS'")
    })
    public void remove(Patient item) {
        patientRepository.delete(item);
    }

    @Override
    @CacheEvict(cacheNames = "patients", allEntries = true)
    public void remove() {
        patientRepository.deleteAll();
    }
}
