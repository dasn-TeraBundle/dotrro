package com.innova.doctrro.ss.service.impl;

import com.innova.doctrro.common.beans.Facility;
import com.innova.doctrro.ss.dao.ReactiveDoctorDao;
import com.innova.doctrro.ss.dao.ReactiveDoctorRatingDao;
import com.innova.doctrro.ss.dao.ReactiveFacilityDao;
import com.innova.doctrro.ss.dto.DoctorDtoResponse;
import com.innova.doctrro.ss.dto.FacilityDtoResponse;
import com.innova.doctrro.ss.service.Converters;
import com.innova.doctrro.ss.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
public class SearchServiceImpl implements SearchService {

    private final ReactiveFacilityDao facilityDao;
    private final ReactiveDoctorDao doctorDao;
    private final ReactiveDoctorRatingDao ratingDao;

    @Autowired
    public SearchServiceImpl(ReactiveFacilityDao facilityDao, ReactiveDoctorDao doctorDao, ReactiveDoctorRatingDao ratingDao) {
        this.facilityDao = facilityDao;
        this.doctorDao = doctorDao;
        this.ratingDao = ratingDao;
    }

    @Override
    public Flux<FacilityDtoResponse> search(double latitude, double longitude, int radius) {
        return facilityDao.findAllWithinDistance(longitude, latitude, radius * 1000)
                .map(Converters::convert);
    }

    @Override
    public Flux<DoctorDtoResponse> search(double latitude, double longitude, int radius, String speciality) {
        return facilityDao.findAllWithinDistance(longitude, latitude, radius * 1000)
                .flatMapIterable(Facility::getDoctors)
                .map(Facility.Practitioner::getRegId)
                .distinct()
                .collectList()
                .flatMapMany(ids -> {
                    if (speciality == null) {
                        return doctorDao.findAllByRegIdIn(ids);
                    } else {
                        return doctorDao.findAllByRegIdInAndSpeciality(ids, speciality);
                    }
                })
                .map(Converters::convert)
                .flatMap(d ->
                        Flux.combineLatest(
                                Flux.just(d),
                                ratingDao.findAverageRatingofDoctor(d.getRegId()).collectList(),
                                (doc, rat) -> {
                                    System.out.println("Rating : " + rat);
                                    if (rat != null && !rat.isEmpty()) {
                                        doc.setRating(rat.get(0).getAvgRating());
                                    }

                                    return doc;
                                }
                        )
                );
    }
}
