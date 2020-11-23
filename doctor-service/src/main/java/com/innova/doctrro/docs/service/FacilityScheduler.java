package com.innova.doctrro.docs.service;


import com.innova.doctrro.common.dto.FacilityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableScheduling
@Component
class FacilityScheduler {

    private final ReactiveFacilityService facilityService;

    private ExecutorService executorService;

    @Autowired
    public FacilityScheduler(ReactiveFacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @Lookup
    NewSlotTask getNewSlotTask(FacilityDto.FacilityDtoResponse dtoResponse, FacilityDto.Practitioner practitioner, boolean init) {
        return null;
    }

    @PostConstruct
    private void init() {
        executorService = Executors.newCachedThreadPool();
    }

    @PreDestroy
    private void cleanup() {
        executorService.shutdown();
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void slotGenerator() {
        facilityService.findAll()
                .subscribe(facilityDtoResponse -> {
                    facilityDtoResponse.getDoctors()
                            .forEach(doctor -> {
                                Runnable task = getNewSlotTask(facilityDtoResponse, doctor, false);
                                executorService.submit(task);
                            });
                });
    }
}
