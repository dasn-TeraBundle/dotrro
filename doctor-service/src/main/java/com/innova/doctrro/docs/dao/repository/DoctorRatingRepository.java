package com.innova.doctrro.docs.dao.repository;

import com.innova.doctrro.docs.beans.DoctorRating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.stream.Stream;

public interface DoctorRatingRepository extends MongoRepository<DoctorRating, String> {

    Stream<DoctorRating> findAllByDoctor_RegId(String regId);
    Stream<DoctorRating> findAllByRatedBy_Email(String email);

    @Query(value = "{doctor.regId: ?0}", fields = "{id: 0, rating: 1}")
    Stream<DoctorRating> findAllRatingByDoctor_RegId(String regId);

}
