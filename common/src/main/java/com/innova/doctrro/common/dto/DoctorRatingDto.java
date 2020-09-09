package com.innova.doctrro.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class DoctorRatingDto {

    private DoctorRatingDto() { }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DoctorRatingDtoRequest {
        @NotNull
        private String doctorRegId;
        @NotNull
        private String doctorName;
        private String ratedByEmail;
        private String ratedByName;
        @NotNull
        @Min(value = 1)
        @Max(value = 5)
        private byte rating;
        @NotNull
        private String comment;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DoctorRatingDtoResponse {
        private String id;
        private String doctorRegId;
        private String doctorName;
        private String ratedByEmail;
        private String ratedByName;
        private byte rating;
        private String comment;
        private Date createdDate;
    }
}
