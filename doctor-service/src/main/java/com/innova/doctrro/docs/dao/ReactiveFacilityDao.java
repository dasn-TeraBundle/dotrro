package com.innova.doctrro.docs.dao;

import com.innova.doctrro.common.dao.ReactiveGenericDao;
import com.innova.doctrro.docs.beans.Facility;
import reactor.core.publisher.Flux;

public interface ReactiveFacilityDao extends ReactiveGenericDao<Facility, String> {

    Flux<Facility> findAllByAdminEmail(String email);
}
