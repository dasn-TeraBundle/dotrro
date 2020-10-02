package com.innova.doctrro.ss.dao.repository;

import com.innova.doctrro.common.beans.DoctorRating;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ReactiveDoctorRatingRepository extends ReactiveMongoRepository<DoctorRating, String> {
}
