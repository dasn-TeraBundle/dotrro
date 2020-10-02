package com.innova.doctrro.docs.dao.repository;

import com.innova.doctrro.common.beans.Facility;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ReactiveFacilityRepository extends ReactiveMongoRepository<Facility, String> {

    @Query(value = "{admins.email: ?0}")
    Flux<Facility> findAllByAdminsContainEmail(String email);

}
