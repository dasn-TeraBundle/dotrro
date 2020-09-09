package com.innova.doctrro.docs.service;

import static com.innova.doctrro.common.dto.DoctorRatingDto.*;
import com.innova.doctrro.docs.beans.Doctor;
import com.innova.doctrro.docs.beans.DoctorRating;

import java.util.List;
import java.util.stream.Collectors;

import static com.innova.doctrro.common.dto.DoctorDto.*;

public class Converters {

    private Converters() {
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
                .map(Converters::convert)
                .collect(Collectors.toList());
    }

    public static class DoctorRatingConverter {

        private DoctorRatingConverter() { }

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
}
