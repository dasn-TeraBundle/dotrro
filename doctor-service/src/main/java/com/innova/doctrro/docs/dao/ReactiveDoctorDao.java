package com.innova.doctrro.docs.dao;

import com.innova.doctrro.common.dao.ReactiveGenericDao;
import com.innova.doctrro.common.beans.Doctor;
import reactor.core.publisher.Mono;

public interface ReactiveDoctorDao extends ReactiveGenericDao<Doctor, String> {

    Mono<Doctor> findByEmail(String email);
}
