package com.innova.doctrro.ss.dao;

import com.innova.doctrro.common.beans.Facility;
import com.innova.doctrro.common.dao.ReactiveGenericDao;
import reactor.core.publisher.Flux;

public interface ReactiveFacilityDao extends ReactiveGenericDao<Facility, String> {
    Flux<Facility> findAllWithinDistance(double longitude, double latitude, int range);
}
