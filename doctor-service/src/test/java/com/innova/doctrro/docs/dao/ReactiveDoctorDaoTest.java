package com.innova.doctrro.docs.dao;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class ReactiveDoctorDaoTest {

    @Autowired
    private ReactiveDoctorDao reactiveDoctorDao;

    @Test
    void findById() {
        StepVerifier.create(reactiveDoctorDao.findById("001").log())
                .expectSubscription()
                .expectNext()
                .verifyComplete();
    }
}
