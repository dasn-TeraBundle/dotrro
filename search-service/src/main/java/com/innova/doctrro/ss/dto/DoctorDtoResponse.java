package com.innova.doctrro.ss.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DoctorDtoResponse implements Serializable {
    private String regId;
    private String name;
    private String sex;
    private float experience;
    private String degree;
    private String speciality;
    private String about;
    private double rating;
    private Set<Clinic> clinics;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Clinic implements Serializable {
        private String id;
        private String name;
        private String type;
    }
}
