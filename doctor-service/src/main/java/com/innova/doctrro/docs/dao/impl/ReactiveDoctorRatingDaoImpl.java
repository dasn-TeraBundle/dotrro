package com.innova.doctrro.docs.dao.impl;

import com.innova.doctrro.common.beans.DoctorRating;
import com.innova.doctrro.docs.dao.ReactiveDoctorRatingDao;
import com.innova.doctrro.docs.dao.repository.ReactiveDoctorRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;


@Component
public class ReactiveDoctorRatingDaoImpl implements ReactiveDoctorRatingDao {

    private final ReactiveDoctorRatingRepository doctorRatingRepository;

    @Autowired
    public ReactiveDoctorRatingDaoImpl(ReactiveDoctorRatingRepository doctorRatingRepository) {
        this.doctorRatingRepository = doctorRatingRepository;
    }

    @Override
    public Mono<DoctorRating> create(DoctorRating item) {
        return doctorRatingRepository.save(item);
    }

    @Override
    public Mono<DoctorRating> findById(String s) {
        return doctorRatingRepository.findById(s);
    }

    @Override
    public Flux<DoctorRating> findAllByDoctorRegId(String regId) {
        return doctorRatingRepository.findAllByDoctor_RegId(regId);
    }

    @Override
    public Flux<Byte> findAllRatingByDoctor_RegId(String regId) {
        return doctorRatingRepository.findAllRatingByDoctor_RegId(regId)
                .map(DoctorRating::getRating);
    }

    @Override
    public Flux<DoctorRating> findAllByRatedByEmail(String email) {
        return doctorRatingRepository.findAllByRatedBy_Email(email);
    }


    @Override
    public Flux<DoctorRating> findAll() {
        return doctorRatingRepository.findAll();
    }

    @Override
    public Mono<DoctorRating> update(String s, DoctorRating item) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove(String s) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove(DoctorRating item) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove() {
        return doctorRatingRepository.deleteAll();
    }
}
