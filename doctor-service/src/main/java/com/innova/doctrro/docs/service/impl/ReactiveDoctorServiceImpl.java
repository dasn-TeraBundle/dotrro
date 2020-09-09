package com.innova.doctrro.docs.service.impl;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;
import static com.innova.doctrro.common.dto.DoctorDto.*;

import com.innova.doctrro.docs.beans.Doctor;
import com.innova.doctrro.docs.dao.ReactiveDoctorDao;
import com.innova.doctrro.docs.exception.DoctorNotFoundException;
import com.innova.doctrro.docs.exception.DuplicateDoctorException;
import com.innova.doctrro.docs.service.Converters;
import com.innova.doctrro.docs.service.ReactiveDoctorService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ReactiveDoctorServiceImpl implements ReactiveDoctorService {

    private final ReactiveDoctorDao reactiveDoctorDao;

    @Autowired
    public ReactiveDoctorServiceImpl(ReactiveDoctorDao reactiveDoctorDao) {
        this.reactiveDoctorDao = reactiveDoctorDao;
    }

    @Override
    public Mono<DoctorDtoResponse> create(DoctorDtoRequest item) {
        Doctor doctor = Converters.convert(item);
        return reactiveDoctorDao.create(doctor)
                .map(Converters::convert)
                .onErrorMap(ex -> {
                    if (ex instanceof DuplicateKeyException)
                        return new DuplicateDoctorException();
                    return ex;
                });
    }

    @Override
    public Mono<DoctorDtoResponse> findById(String s) {
        return reactiveDoctorDao.findById(s)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new DoctorNotFoundException()) ))
                .map(Converters::convert);
    }

    @Override
    public Mono<DoctorDtoResponse> findByEmail(String email) {
        return reactiveDoctorDao.findByEmail(email)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new DoctorNotFoundException()) ))
                .map(Converters::convert);
    }

    @Override
    public Flux<DoctorDtoResponse> findAll() {
        return reactiveDoctorDao.findAll()
                .map(Converters::convert);
    }

    @Override
    public Mono<DoctorDtoResponse> update(String email, DoctorDtoRequest item) {
        return reactiveDoctorDao.findByEmail(email)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new DoctorNotFoundException()) ))
                .flatMap(doctor -> {
                    if (item.getSpeciality() != null)
                        doctor.getAbout().setSpeciality(item.getSpeciality());
                    if (item.getDegree() != null)
                        doctor.getAbout().setDegree(item.getDegree());
                    if (item.getAbout() != null)
                        doctor.getAbout().setAbout(item.getAbout());

                    return reactiveDoctorDao.update(email, doctor);
                })
                .map(Converters::convert);
    }

    @Override
    public Mono<Void> remove(String s) {
        return reactiveDoctorDao.findById(s)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new DoctorNotFoundException()) ))
                .flatMap(reactiveDoctorDao::remove);
    }

    @Override
    public Mono<Void> remove(DoctorDtoRequest item) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove() {
        return reactiveDoctorDao.remove();
    }
}
