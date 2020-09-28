package com.innova.doctrro.ss.dao;

import com.innova.doctrro.common.beans.Doctor;
import com.innova.doctrro.common.dao.ReactiveGenericDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ReactiveDoctorDao extends ReactiveGenericDao<Doctor, String> {

    Flux<Doctor> findAllByRegIdIn(List<String> regIds);
}
