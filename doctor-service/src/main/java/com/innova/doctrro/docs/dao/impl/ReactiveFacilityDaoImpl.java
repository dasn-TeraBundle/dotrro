package com.innova.doctrro.docs.dao.impl;

import com.innova.doctrro.common.beans.Facility;
import com.innova.doctrro.docs.dao.ReactiveFacilityDao;
import com.innova.doctrro.docs.dao.repository.ReactiveFacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;


@Component
public class ReactiveFacilityDaoImpl implements ReactiveFacilityDao {

    private final ReactiveFacilityRepository facilityRepository;

    @Autowired
    public ReactiveFacilityDaoImpl(ReactiveFacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Override
    public Mono<Facility> create(Facility item) {
        return facilityRepository.save(item);
    }

    @Override
    public Mono<Facility> findById(String s) {
        return facilityRepository.findById(s);
    }

    @Override
    public Flux<Facility> findAllByAdminEmail(String email) {
        return facilityRepository.findAllByAdminsContainEmail(email);
    }

    @Override
    public Flux<Facility> findAll() {
        return facilityRepository.findAll();
    }

    @Override
    public Mono<Facility> update(String s, Facility item) {
        return facilityRepository.save(item);
    }

    @Override
    public Mono<Void> remove(String s) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove(Facility item) {
        return facilityRepository.delete(item);
    }

    @Override
    public Mono<Void> remove() {
        return facilityRepository.deleteAll();
    }
}
