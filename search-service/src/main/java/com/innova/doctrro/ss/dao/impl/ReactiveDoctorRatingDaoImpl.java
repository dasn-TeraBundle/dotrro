package com.innova.doctrro.ss.dao.impl;

import com.innova.doctrro.common.beans.DoctorRating;
import com.innova.doctrro.ss.beans.Ratings;
import com.innova.doctrro.ss.dao.ReactiveDoctorRatingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
public class ReactiveDoctorRatingDaoImpl implements ReactiveDoctorRatingDao {

    private final ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public ReactiveDoctorRatingDaoImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Flux<Ratings> findAverageRatingofDoctor(String regId) {
        TypedAggregation<DoctorRating> agg = newAggregation(DoctorRating.class,
                match(Criteria.where("doctor.regId").is(regId)),
                group("doctor.regId")
                        .avg("rating").as("avgRating"),
                limit(1)
//                project("rating").and("doctor.regId").as("doctorId")
        );

        return mongoTemplate.aggregate(agg, "doctor_ratings", Ratings.class);
    }
}
