package com.innova.doctrro.docs.service.mock;


import com.innova.doctrro.docs.dao.ReactiveDoctorDao;
import com.innova.doctrro.docs.service.impl.ReactiveDoctorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReactiveDoctorServiceTest {

    @InjectMocks
    private ReactiveDoctorServiceImpl doctorService;
    @Mock
    private ReactiveDoctorDao doctorDao;

    @Test
    void findById() {}
}
