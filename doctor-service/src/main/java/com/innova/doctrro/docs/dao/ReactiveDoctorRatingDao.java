package com.innova.doctrro.docs.dao;

import com.innova.doctrro.common.dao.ReactiveGenericDao;
import com.innova.doctrro.common.beans.DoctorRating;
import reactor.core.publisher.Flux;

public interface ReactiveDoctorRatingDao extends ReactiveGenericDao<DoctorRating, String> {

    Flux<DoctorRating> findAllByDoctorRegId(String regId);
    Flux<Byte> findAllRatingByDoctor_RegId(String regId);
    Flux<DoctorRating> findAllByRatedByEmail(String email);
}
