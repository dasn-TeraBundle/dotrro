package com.innova.doctrro.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;


public class DoctorDto {

    private DoctorDto() {
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DoctorDtoRequest implements Serializable {
        @NotNull
        private String regId;
        private String email;
        private String name;
        private String dob;
        @NotNull
        @Pattern(regexp = "M|F|O")
        private String sex;
        private float experience;
        @NotNull
        private String degree;
        @NotNull
        private String speciality;
        private String about;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DoctorDtoResponse implements Serializable {
        private String regId;
        private Set<String> emails;
        private String name;
        private String dob;
        private String sex;
        private float experience;
        private String degree;
        private String speciality;
        private String about;
    }
}
