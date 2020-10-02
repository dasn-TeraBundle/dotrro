package com.innova.doctrro.docs.dao.repository;

import com.innova.doctrro.common.beans.Doctor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ReactiveDoctorRepository extends ReactiveMongoRepository<Doctor, String> {

    Mono<Doctor> findByEmailsContains(String email);
}
