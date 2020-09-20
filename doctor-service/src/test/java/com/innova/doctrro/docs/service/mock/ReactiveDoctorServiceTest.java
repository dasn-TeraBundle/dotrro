package com.innova.doctrro.docs.service.mock;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.innova.doctrro.common.constants.Gender;
import com.innova.doctrro.common.dto.KafkaMessage;
import com.innova.doctrro.common.service.KafkaService;
import com.innova.doctrro.docs.beans.Doctor;
import com.innova.doctrro.docs.dao.ReactiveDoctorDao;
import com.innova.doctrro.docs.exception.DoctorDBExceptionFactory;
import com.innova.doctrro.docs.service.impl.ReactiveDoctorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.innova.doctrro.common.dto.DoctorDto.DoctorDtoRequest;
import static com.innova.doctrro.docs.service.Converters.DoctorConverter;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReactiveDoctorServiceTest {

    private static final String KAFKA_DOCTOR_TOPIC = "users_create";

    @InjectMocks
    private ReactiveDoctorServiceImpl doctorService;

    @Mock
    private ReactiveDoctorDao doctorDao;
    @Mock
    private KafkaService kafkaService;

    @Mock
    private Doctor doctor;
    @Mock
    private Doctor.Personal personal;
    @Mock
    private Doctor.About about;

    @Test
    void create() {
        DoctorDtoRequest request = createDoctorDtoRequest();
        Doctor rDoctor = DoctorConverter.convert(request);

        when(doctorDao.create(rDoctor)).thenReturn(Mono.just(doctor));

        when(doctor.getRegId()).thenReturn("00001");
        when(doctor.getPersonal()).thenReturn(personal);
        when(personal.getSex()).thenReturn(Gender.M);
        when(doctor.getAbout()).thenReturn(about);

        StepVerifier.create(doctorService.create(request).log())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void create_Duplicate() {
        DoctorDtoRequest request = createDoctorDtoRequest();
        Doctor rDoctor = DoctorConverter.convert(request);

        when(doctorDao.create(rDoctor)).thenReturn(Mono.error(new DuplicateKeyException("DUplicate key")));

        StepVerifier.create(doctorService.create(request).log())
                .expectSubscription()
                .expectError(DoctorDBExceptionFactory.DuplicateDoctorException.class)
                .verify();
    }

    @Test
    void create_Kafka_JsonException() throws JsonProcessingException {
        DoctorDtoRequest request = createDoctorDtoRequest();
        Doctor rDoctor = DoctorConverter.convert(request);
        var kafkaMsg = new KafkaMessage(request.getEmail(), "C", "doctors");

        when(doctorDao.create(rDoctor)).thenReturn(Mono.just(doctor));
        doThrow(JsonProcessingException.class).when(kafkaService).send(KAFKA_DOCTOR_TOPIC, kafkaMsg);
        when(doctorDao.remove(doctor)).thenReturn(Mono.empty());

        StepVerifier.create(doctorService.create(request).log())
                .expectSubscription()
                .expectError(RuntimeException.class)
                .verify();
    }

    private DoctorDtoRequest createDoctorDtoRequest() {
        var request = new DoctorDtoRequest();
        request.setRegId("00001");
        request.setName("Dr. Test");
        request.setEmail("test@innova.com");
        request.setSex("M");
        request.setDob("13-08-1991");
        request.setExperience(5.2f);
        request.setDegree("MBBS");
        request.setSpeciality("General");
        return request;
    }

}
