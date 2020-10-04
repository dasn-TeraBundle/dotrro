package com.innova.doctrro.docs.service;

import com.innova.doctrro.common.dto.FacilityDto;
import com.innova.doctrro.common.beans.Doctor;
import com.innova.doctrro.common.beans.DoctorRating;
import com.innova.doctrro.common.beans.Facility;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.innova.doctrro.common.dto.DoctorDto.DoctorDtoRequest;
import static com.innova.doctrro.common.dto.DoctorDto.DoctorDtoResponse;
import static com.innova.doctrro.common.dto.DoctorRatingDto.DoctorRatingDtoRequest;
import static com.innova.doctrro.common.dto.DoctorRatingDto.DoctorRatingDtoResponse;
import static com.innova.doctrro.common.dto.FacilityDto.FacilityDtoRequest;
import static com.innova.doctrro.common.dto.FacilityDto.FacilityDtoResponse;
import static com.innova.doctrro.common.beans.Facility.Location;

public class Converters {

    private Converters() {
    }

    public static class DoctorConverter {

        private DoctorConverter() {
        }

        public static Doctor convert(DoctorDtoRequest request) {
            var personal = new Doctor.Personal(request.getDob(), request.getSex());
            var about = new Doctor.About(request.getExperience(), request.getDegree(), request.getSpeciality(), request.getAbout());

            return new Doctor(request.getRegId(), request.getEmail(), request.getName(), personal, about);
        }

        public static DoctorDtoResponse convert(Doctor doctor) {
            var resp = new DoctorDtoResponse();

            resp.setRegId(doctor.getRegId());
            resp.setEmails(doctor.getEmails());
            resp.setName(doctor.getName());

            resp.setDob(doctor.getPersonal().getDob());
            resp.setSex(doctor.getPersonal().getSex().toString());

            resp.setExperience(doctor.getAbout().getExperience());
            resp.setDegree(doctor.getAbout().getDegree());
            resp.setSpeciality(doctor.getAbout().getSpeciality());
            resp.setAbout(doctor.getAbout().getAbout());

            return resp;
        }

        public static List<DoctorDtoResponse> convert(List<Doctor> doctors) {
            return doctors.stream()
                    .map(DoctorConverter::convert)
                    .collect(Collectors.toList());
        }
    }

    public static class DoctorRatingConverter {

        private DoctorRatingConverter() {
        }

        public static DoctorRating convert(DoctorRatingDtoRequest req) {
            var doctor = new DoctorRating.RatedDoctor(req.getDoctorRegId(), req.getDoctorName());
            var ratedBy = new DoctorRating.User(req.getRatedByEmail(), req.getRatedByName());

            return new DoctorRating(doctor, ratedBy, req.getRating(), req.getComment());
        }

        public static DoctorRatingDtoResponse convert(DoctorRating rating) {
            var resp = new DoctorRatingDtoResponse();

            resp.setId(rating.getId());

            resp.setDoctorRegId(rating.getDoctor().getRegId());
            resp.setDoctorName(rating.getDoctor().getName());

            resp.setRatedByEmail(rating.getRatedBy().getEmail());
            resp.setRatedByName(rating.getRatedBy().getName());

            resp.setRating(rating.getRating());
            resp.setComment(rating.getComment());
            resp.setCreatedDate(rating.getCreatedDate());

            return resp;
        }

        public static List<DoctorRatingDtoResponse> convert(List<DoctorRating> ratings) {
            return ratings.stream()
                    .map(DoctorRatingConverter::convert)
                    .collect(Collectors.toList());
        }
    }

    public static class FacilityConverter {

        private FacilityConverter() {
        }

        public static Facility convert(FacilityDtoRequest request) {
            var doctors = request.getDoctors().stream()
                    .map(d -> {
                        var slots = d.getSlots()
                                .stream()
                                .map(slot -> new Facility.Practitioner.Slot(
                                        slot.getDayOfWeek(),
                                        slot.getStartTime(),
                                        slot.getEndTime(),
                                        slot.getDuration(),
                                        slot.isAutoApproveEnabled()
                                ))
                                .collect(Collectors.toList());
                        return new Facility.Practitioner(d.getRegId(), d.getRegId(), new LinkedHashSet<>(slots));
                    })
                    .collect(Collectors.toList());

            return new Facility(
                    request.getName(),
                    request.getType(),
                    doctors,
                    Arrays.asList(new Facility.Admin(request.getAdmin().getEmail(), request.getAdmin().getName())),
                    new Location(request.getLongitude(), request.getLatitude())
            );
        }

        public static FacilityDtoResponse convert(Facility facility) {
            var doctors = facility.getDoctors().stream()
                    .map(d -> {
                        var slots = d.getSlots()
                                .stream()
                                .map(slot -> new FacilityDto.Practitioner.Slot(
                                        slot.getDayOfWeek(),
                                        slot.getStartTime(),
                                        slot.getEndTime(),
                                        slot.getDuration(),
                                        slot.isAutoApproveEnabled()
                                ))
                                .collect(Collectors.toList());
                        return new FacilityDto.Practitioner(d.getRegId(), d.getRegId(), slots);
                    })
                    .collect(Collectors.toList());

            return new FacilityDtoResponse(
                    facility.getId(),
                    facility.getName(),
                    facility.getType(),
                    doctors,
                    facility.getAdmins().stream().map(u -> new FacilityDto.Admin(u.getEmail(), u.getName())).collect(Collectors.toList())
            );
        }
    }
}
