package com.innova.doctrro.ps.dao.repository;

import com.innova.doctrro.ps.beans.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientRepository extends MongoRepository<Patient, String> {
}
