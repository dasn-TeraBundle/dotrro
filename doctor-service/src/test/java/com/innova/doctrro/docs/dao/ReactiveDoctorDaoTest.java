package com.innova.doctrro.docs.dao;


import com.innova.doctrro.common.beans.Doctor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
public class ReactiveDoctorDaoTest {

    @Autowired
    private ReactiveDoctorDao reactiveDoctorDao;

    private Doctor doctor;

    @BeforeEach
    void setup() {
        var personal = new Doctor.Personal();
        var about = new Doctor.About();
        doctor = new Doctor("0001", "test@doctrro.innova.com", "Dr. Test", personal, about);
        reactiveDoctorDao.create(doctor).block();
    }

    @Test
    void findById_Empty() {
        StepVerifier.create(reactiveDoctorDao.findById("001").log())
                .expectSubscription()
                .expectNext()
                .verifyComplete();
    }

    @Test
    void findById() {
        StepVerifier.create(reactiveDoctorDao.findById("0001").log())
                .expectSubscription()
                .expectNext(doctor)
                .verifyComplete();
    }

    @Test
    void findByEmail() {
        StepVerifier.create(reactiveDoctorDao.findByEmail("test@doctrro.innova.com").log())
                .expectSubscription()
                .expectNext(doctor)
                .verifyComplete();
    }

    @Test
    void findAll() {
        StepVerifier.create(reactiveDoctorDao.findAll().log())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void update() {
        doctor.setName("Dr. New Name");

        StepVerifier.create(reactiveDoctorDao.update(doctor.getRegId(), doctor).log())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void removeById() {
        StepVerifier.create(reactiveDoctorDao.remove("0001").log())
                .expectSubscription()
                .expectError(UnsupportedOperationException.class)
                .verify();
    }

    @Test
    void remove() {
        Flux<Doctor> doctorFlux = reactiveDoctorDao.remove(doctor)
                .flatMapMany(a -> reactiveDoctorDao.findAll());

        StepVerifier.create(doctorFlux.log())
                .expectSubscription()
                .expectNextCount(0)
                .verifyComplete();
    }

    @AfterEach
    void cleanup() {
        reactiveDoctorDao.remove().block();
    }
}
