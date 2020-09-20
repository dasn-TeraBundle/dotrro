package com.innova.doctrro.docs.dao.repository;

import com.innova.doctrro.docs.beans.DoctorRating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

public interface ReactiveDoctorRatingRepository extends ReactiveMongoRepository<DoctorRating, String> {

    @Query(sort = "{createdDate: -1}")
    Flux<DoctorRating> findAllByDoctor_RegId(String regId);
    @Query(sort = "{createdDate: -1}")
    Flux<DoctorRating> findAllByRatedBy_Email(String email);

    @Query(value = "{'doctor.regId': ?0}", fields = "{id: 0, rating: 1}")
    Flux<DoctorRating> findAllRatingByDoctor_RegId(String regId);

}
