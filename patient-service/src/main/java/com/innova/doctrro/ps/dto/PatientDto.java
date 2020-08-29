package com.innova.doctrro.ps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.innova.doctrro.ps.beans.Patient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.innova.doctrro.ps.beans.Patient.*;

public class PatientDto {

    private PatientDto() {
    }

    public static Patient convert(PatientDtoRequest req) {
        var personal = new Personal(req.getSex(), req.getDob(), req.getBloodGroup(), req.getHeight(), req.getWeight());
        var lifestyle = new Lifestyle(req.getSmoke(), req.getAlcohol(), req.getDiet(), req.getActivity(), req.getMarried());
        var medical = new Medical(req.getChronic());

        return new Patient("", "", personal, lifestyle, medical);
    }

    public static PatientDtoResponse convert(Patient patient) {
        var resp = new PatientDtoResponse();

        resp.setEmail(patient.getEmail());
        resp.setName(patient.getName());

        resp.setSex(patient.getPersonal().getSex().toString());
        resp.setWeight(patient.getPersonal().getWeight());

        resp.setActivity(patient.getLifestyle().getActivity());

        resp.setChronic(patient.getMedical().getChronic());

        return resp;
    }

    public static List<PatientDtoResponse> convert(List<Patient> patients) {
        return patients
                .stream()
                .map(PatientDto::convert)
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PatientDtoRequest {
        private String sex;
        private String dob;
        private String bloodGroup;
        private String height;
        private String weight;

        private String smoke;
        private String alcohol;
        private String diet;
        private String activity;
        private String married;

        private Set<String> chronic;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PatientDtoResponse extends PatientDtoRequest {
        private String email;
        private String name;
    }
}
