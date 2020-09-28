package com.innova.doctrro.ss.dao.repository;

import com.innova.doctrro.common.beans.Doctor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ReactiveDoctorRepository extends ReactiveMongoRepository<Doctor, String> {

    Flux<Doctor> findAllByRegIdIn(List<String> regIds);
}
