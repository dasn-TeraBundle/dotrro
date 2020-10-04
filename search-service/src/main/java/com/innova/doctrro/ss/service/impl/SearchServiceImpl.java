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
import reactor.core.publisher.Mono;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    public Mono<FacilityDtoResponse> findAllBookingSlots(String facilityId, String doctorRegId) {
        return facilityDao.findById(facilityId)
//                .switchIfEmpty(Mono.defer(() -> Mono.error(FacilityDBExceptionFactory.createException(DATA_NOT_FOUND))))
                .flatMap(f -> {
                    var doctor = f.getDoctors().stream().filter(d -> d.getRegId().equals(doctorRegId)).findFirst();
//                    if (doctor.isEmpty()) {
//                        return Mono.error(DoctorDBExceptionFactory.createException(DATA_NOT_FOUND));
//                    } else {
                    f.setDoctors(Arrays.asList(doctor.get()));
                    return Mono.just(f);
//                    }
                })
                .map(f -> {
                    var slots = new ArrayList<Facility.Practitioner.Slot>();//f.getDoctors().get(0).getSlots();
                    slots.add(new Facility.Practitioner.Slot(DayOfWeek.MONDAY, "18:00", "19:00", (byte) 30, true));
//                    slots.add(new Facility.Practitioner.Slot(DayOfWeek.FRIDAY, "10:00", "11:00", (byte)30, true));

                    var start = LocalDate.now().plusDays(1).atStartOfDay();
                    var end = start.plusMonths(1);
                    var allSlots = slots.stream()  //Loop over all slots from db
                            .flatMap(slot -> {
                                byte duration = slot.getDuration();

                                //Loop over each day in week for a db slot
                                return Stream.iterate(start, d -> d.isBefore(end), d -> d.plusDays(7))
                                        .filter(d -> slot.getDayOfWeek() == d.getDayOfWeek())
                                        .flatMap(d -> {
                                            LocalTime startTime = LocalTime.parse(slot.getStartTime());
                                            LocalTime endTime = LocalTime.parse(slot.getEndTime());

                                            //Loop over each time frame in a day
                                            return Stream.iterate(startTime, t -> t.isBefore(endTime), t -> t.plusMinutes(duration))
                                                    .map(t -> {
                                                        var dt = LocalDateTime.of(d.toLocalDate(), t);
                                                        return new FacilityDtoResponse.Practitioner.Slot(dt, dt.plusMinutes(duration));
                                                    });
                                        });
                            }).collect(Collectors.toList());
                    var resp = Converters.convert(f);
                    resp.getPractitioners().get(0).setSlots(allSlots);

                    return resp;
                });
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
