package com.innova.doctrro.ss.service;

import com.innova.doctrro.common.beans.Doctor;
import com.innova.doctrro.common.beans.Facility;
import com.innova.doctrro.ss.dto.DoctorDtoResponse;
import com.innova.doctrro.ss.dto.FacilityDtoResponse;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.innova.doctrro.ss.dto.FacilityDtoResponse.Practitioner;

public class Converters {

    private Converters() {
    }

    public static DoctorDtoResponse convert(Doctor doctor) {
        var resp = new DoctorDtoResponse();

        resp.setRegId(doctor.getRegId());
        resp.setName(doctor.getName());

        resp.setSex(doctor.getPersonal().getSex().toString());

        resp.setExperience(doctor.getAbout().getExperience());
        resp.setDegree(doctor.getAbout().getDegree());
        resp.setSpeciality(doctor.getAbout().getSpeciality());
        resp.setAbout(doctor.getAbout().getAbout());

        return resp;
    }


    public static FacilityDtoResponse convert(Facility facility) {
        List<Practitioner> practitioners = facility.getDoctors()
                .stream()
                .map(d -> new Practitioner(d.getRegId(), d.getName()))
                .collect(Collectors.toList());

        return new FacilityDtoResponse(
                facility.getId(),
                facility.getName(),
                facility.getType(),
                practitioners);
    }
}
