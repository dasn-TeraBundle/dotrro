package com.innova.doctrro.docs.service.impl;

import com.innova.doctrro.common.beans.DoctorRating;
import com.innova.doctrro.docs.dao.ReactiveDoctorRatingDao;
import com.innova.doctrro.docs.service.ReactiveDoctorRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;
import static com.innova.doctrro.common.dto.DoctorRatingDto.DoctorRatingDtoRequest;
import static com.innova.doctrro.common.dto.DoctorRatingDto.DoctorRatingDtoResponse;
import static com.innova.doctrro.docs.service.Converters.DoctorRatingConverter;


@Service
public class ReactiveDoctorRatingServiceImpl implements ReactiveDoctorRatingService {

    private final ReactiveDoctorRatingDao doctorRatingDao;

    @Autowired
    public ReactiveDoctorRatingServiceImpl(ReactiveDoctorRatingDao doctorRatingDao) {
        this.doctorRatingDao = doctorRatingDao;
    }

    @Override
    public Mono<DoctorRatingDtoResponse> create(DoctorRatingDtoRequest item) {
        DoctorRating rating = DoctorRatingConverter.convert(item);

        return doctorRatingDao.create(rating)
                .map(DoctorRatingConverter::convert);
    }

    @Override
    public Mono<DoctorRatingDtoResponse> findById(String s) {
        return doctorRatingDao.findById(s)
                .map(DoctorRatingConverter::convert);
    }

    @Override
    public Flux<DoctorRatingDtoResponse> findAllByDoctorRegId(String regId) {
        return doctorRatingDao.findAllByDoctorRegId(regId)
                .map(DoctorRatingConverter::convert);
    }

    @Override
    public Mono<Double> findAverageRatingByDoctorRegId(String regId) {
        return doctorRatingDao.findAllRatingByDoctor_RegId(regId)
                .collect(Collectors.averagingDouble(v -> (double) v));
    }

    @Override
    public Flux<DoctorRatingDtoResponse> findAllByRatedByEmail(String email) {
        return doctorRatingDao.findAllByRatedByEmail(email)
                .map(DoctorRatingConverter::convert);
    }

    @Override
    public Flux<DoctorRatingDtoResponse> findAll() {
        return doctorRatingDao.findAll()
                .map(DoctorRatingConverter::convert);
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
    public Mono<Void> remove() {
        return doctorRatingDao.remove();
    }
}
