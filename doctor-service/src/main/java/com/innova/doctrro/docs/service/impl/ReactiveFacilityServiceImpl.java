package com.innova.doctrro.docs.service.impl;

import com.innova.doctrro.docs.dao.ReactiveFacilityDao;
import com.innova.doctrro.docs.exception.FacilityDBExceptionFactory;
import com.innova.doctrro.docs.service.ReactiveFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.innova.doctrro.common.constants.DBExceptionType.DATA_NOT_FOUND;
import static com.innova.doctrro.common.constants.DBExceptionType.DUPLICATE_KEY;
import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;
import static com.innova.doctrro.common.dto.FacilityDto.FacilityDtoRequest;
import static com.innova.doctrro.common.dto.FacilityDto.FacilityDtoResponse;
import static com.innova.doctrro.docs.service.Converters.FacilityConverter;


@Service
public class ReactiveFacilityServiceImpl implements ReactiveFacilityService {

    private final ReactiveFacilityDao facilityDao;

    @Autowired
    public ReactiveFacilityServiceImpl(ReactiveFacilityDao facilityDao) {
        this.facilityDao = facilityDao;
    }

    @Override
    public Mono<FacilityDtoResponse> create(FacilityDtoRequest item) {
        return Mono.just(FacilityConverter.convert(item))
                .flatMap(facilityDao::create)
                .map(FacilityConverter::convert)
                .onErrorMap(ex -> {
                    if (ex instanceof DuplicateKeyException)
                        return FacilityDBExceptionFactory.createException(DUPLICATE_KEY);
                    return ex;
                });
    }

    @Override
    public Mono<FacilityDtoResponse> findById(String s) {
        return facilityDao.findById(s)
                .switchIfEmpty(Mono.defer(() -> Mono.error(FacilityDBExceptionFactory.createException(DATA_NOT_FOUND))))
                .map(FacilityConverter::convert);
    }

    @Override
    public Flux<FacilityDtoResponse> findAllByAdminEmail(String email) {
        return facilityDao.findAllByAdminEmail(email)
                .switchIfEmpty(Mono.defer(() -> Mono.error(FacilityDBExceptionFactory.createException(DATA_NOT_FOUND))))
                .map(FacilityConverter::convert);
    }

    @Override
    public Flux<FacilityDtoResponse> findAll() {
        return facilityDao.findAll()
                .map(FacilityConverter::convert);
    }

    @Override
    public Mono<FacilityDtoResponse> update(String s, FacilityDtoRequest item) {
        return facilityDao.findById(s)
                .switchIfEmpty(Mono.defer(() -> Mono.error(FacilityDBExceptionFactory.createException(DATA_NOT_FOUND))))
                .flatMap(facility -> {
                    facility.setName(item.getName());

                    return facilityDao.update(s, facility);
                }).map(FacilityConverter::convert);
    }

    @Override
    public Mono<Void> remove(String s) {
        return facilityDao.findById(s)
                .switchIfEmpty(Mono.defer(() -> Mono.error(FacilityDBExceptionFactory.createException(DATA_NOT_FOUND))))
                .flatMap(facilityDao::remove);
    }

    @Override
    public Mono<Void> remove(FacilityDtoRequest item) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove() {
        return facilityDao.remove();
    }
}
