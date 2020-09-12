package com.innova.doctrro.docs.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.innova.doctrro.common.dto.KafkaMessage;
import com.innova.doctrro.common.service.KafkaService;
import com.innova.doctrro.docs.beans.Doctor;
import com.innova.doctrro.docs.dao.ReactiveDoctorDao;
import com.innova.doctrro.docs.exception.DoctorNotFoundException;
import com.innova.doctrro.docs.exception.DuplicateDoctorException;
import com.innova.doctrro.docs.service.ReactiveDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;
import static com.innova.doctrro.common.dto.DoctorDto.DoctorDtoRequest;
import static com.innova.doctrro.common.dto.DoctorDto.DoctorDtoResponse;
import static com.innova.doctrro.docs.service.Converters.DoctorConverter;


@Service
public class ReactiveDoctorServiceImpl implements ReactiveDoctorService {

    private static final String KAFKA_DOCTOR_TOPIC = "users_create";

    private final ReactiveDoctorDao reactiveDoctorDao;
    private final KafkaService kafkaService;

    @Autowired
    public ReactiveDoctorServiceImpl(ReactiveDoctorDao reactiveDoctorDao, KafkaService kafkaService) {
        this.reactiveDoctorDao = reactiveDoctorDao;
        this.kafkaService = kafkaService;
    }

    @Override
    public Mono<DoctorDtoResponse> create(DoctorDtoRequest item) {
        Doctor doctor1 = DoctorConverter.convert(item);
        return reactiveDoctorDao.create(doctor1)
                .flatMap(doctor -> {
                    var kafkaMsg = new KafkaMessage(item.getEmail(), "C", "doctors");
                    try {
                        kafkaService.send(KAFKA_DOCTOR_TOPIC, kafkaMsg);
                    } catch (JsonProcessingException e) {
                        reactiveDoctorDao.remove(doctor);
                        return Mono.defer(() -> Mono.error(new RuntimeException("")));
                    }

                    return Mono.just(DoctorConverter.convert(doctor));
                })
                .onErrorMap(ex -> {
                    if (ex instanceof DuplicateKeyException)
                        return new DuplicateDoctorException();
                    return ex;
                });
    }

    @Override
    public Mono<DoctorDtoResponse> findById(String s) {
        return reactiveDoctorDao.findById(s)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new DoctorNotFoundException())))
                .map(DoctorConverter::convert);
    }

    @Override
    public Mono<DoctorDtoResponse> findByEmail(String email) {
        return reactiveDoctorDao.findByEmail(email)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new DoctorNotFoundException())))
                .map(DoctorConverter::convert);
    }

    @Override
    public Flux<DoctorDtoResponse> findAll() {
        return reactiveDoctorDao.findAll()
                .map(DoctorConverter::convert);
    }

    @Override
    public Mono<DoctorDtoResponse> addEmail(String regId, String newEmail) {
        return reactiveDoctorDao.findById(regId)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new DoctorNotFoundException())))
                .flatMap(doctor -> {
                    doctor.addEmail(newEmail);

                    return reactiveDoctorDao.update(regId, doctor);
                })
                .flatMap(doctor -> {
                    var emailUpdate = new KafkaMessage.Update("email", null, newEmail);
                    var nameUpdate = new KafkaMessage.Update("name", null, doctor.getName());
                    var kafkaMsg = new KafkaMessage(newEmail, "U", "doctors");
                    kafkaMsg.addUpdate(emailUpdate);
                    kafkaMsg.addUpdate(nameUpdate);
                    try {
                        kafkaService.send(KAFKA_DOCTOR_TOPIC, kafkaMsg);
                    } catch (JsonProcessingException e) {
                        doctor.getEmails().remove(newEmail);
                        reactiveDoctorDao.update(regId, doctor);
                        return Mono.defer(() -> Mono.error(new RuntimeException("")));
                    }

                    return Mono.just(DoctorConverter.convert(doctor));
                });
    }

    @Override
    public Mono<DoctorDtoResponse> update(String email, DoctorDtoRequest item) {
        return reactiveDoctorDao.findByEmail(email)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new DoctorNotFoundException())))
                .flatMap(doctor -> {
                    if (item.getSpeciality() != null)
                        doctor.getAbout().setSpeciality(item.getSpeciality());
                    if (item.getDegree() != null)
                        doctor.getAbout().setDegree(item.getDegree());
                    if (item.getAbout() != null)
                        doctor.getAbout().setAbout(item.getAbout());

                    return reactiveDoctorDao.update(doctor.getRegId(), doctor);
                })
                .map(DoctorConverter::convert);
    }

    @Override
    public Mono<Void> remove(String s) {
        return reactiveDoctorDao.findById(s)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new DoctorNotFoundException())))
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
