package com.innova.doctrro.ss.dao.repository;

import com.innova.doctrro.common.beans.Facility;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ReactiveFacilityRepository extends ReactiveMongoRepository<Facility, String> {

    @Query(value = "{" +
            "     location: " +
            "       { $near: " +
            "          {" +
            "            $geometry: { type: 'Point',  coordinates: [ ?0, ?1 ] }," +
            "            $maxDistance: ?2" +
            "          }" +
            "       }" +
            "   }", fields = "{admins: 0}")
    Flux<Facility> findAllWithinDistance(double longitude, double latitude, int range);
}
