package com.innova.doctrro.docs.dao.repository;

import com.innova.doctrro.common.beans.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DoctorRepository extends MongoRepository<Doctor, String> {

    Doctor findByEmailsContains(String email);
}
