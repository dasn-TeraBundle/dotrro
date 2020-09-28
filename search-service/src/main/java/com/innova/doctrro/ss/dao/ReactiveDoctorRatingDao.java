package com.innova.doctrro.ss.dao;

import com.innova.doctrro.ss.beans.Ratings;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveDoctorRatingDao {

    Flux<Ratings> findAverageRatingofDoctor(String regId);
}
