package com.innova.doctrro.docs.service.data;


import com.innova.doctrro.docs.exception.DoctorNotFoundException;
import com.innova.doctrro.docs.service.ReactiveDoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
public class ReactiveDoctorServiceTest {

    @Autowired
    private ReactiveDoctorService doctorService;

    @Test
    void findById() {
        StepVerifier.create(doctorService.findById("001"))
                .expectSubscription()
                .expectError(DoctorNotFoundException.class)
                .verify();
    }

}
