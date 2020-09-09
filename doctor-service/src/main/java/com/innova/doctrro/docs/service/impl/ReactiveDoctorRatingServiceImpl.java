package com.innova.doctrro.docs.service.impl;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;
import static com.innova.doctrro.common.dto.DoctorRatingDto.*;

import com.innova.doctrro.docs.beans.DoctorRating;
import com.innova.doctrro.docs.dao.ReactiveDoctorRatingDao;
import com.innova.doctrro.docs.service.Converters;
import com.innova.doctrro.docs.service.ReactiveDoctorRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;


@Service
public class ReactiveDoctorRatingServiceImpl implements ReactiveDoctorRatingService {

    private final ReactiveDoctorRatingDao doctorRatingDao;

    @Autowired
    public ReactiveDoctorRatingServiceImpl(ReactiveDoctorRatingDao doctorRatingDao) {
        this.doctorRatingDao = doctorRatingDao;
    }

    @Override
    public Mono<DoctorRatingDtoResponse> create(DoctorRatingDtoRequest item) {
        DoctorRating rating = Converters.DoctorRatingConverter.convert(item);

        return doctorRatingDao.create(rating)
                .map(Converters.DoctorRatingConverter::convert);
    }

    @Override
    public Mono<DoctorRatingDtoResponse> findById(String s) {
        return doctorRatingDao.findById(s)
                .map(Converters.DoctorRatingConverter::convert);
    }

    @Override
    public Flux<DoctorRatingDtoResponse> findAllByDoctorRegId(String regId) {
        return doctorRatingDao.findAllByDoctorRegId(regId)
                .map(Converters.DoctorRatingConverter::convert);
    }

    @Override
    public Mono<Double> findAllRatingByDoctor_RegId(String regId) {
        return doctorRatingDao.findAllRatingByDoctor_RegId(regId)
                .collect(Collectors.averagingDouble(v -> (double)v));
    }

    @Override
    public Flux<DoctorRatingDtoResponse> findAllByRatedByEmail(String email) {
        return doctorRatingDao.findAllByRatedByEmail(email)
                .map(Converters.DoctorRatingConverter::convert);
    }

    @Override
    public Flux<DoctorRatingDtoResponse> findAll() {
        return doctorRatingDao.findAll()
                .map(Converters.DoctorRatingConverter::convert);
    }

    @Override
    public Mono<DoctorRatingDtoResponse> update(String s, DoctorRatingDtoRequest item) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove(String s) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove(DoctorRatingDtoRequest item) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove() {
        return doctorRatingDao.remove();
    }
}
