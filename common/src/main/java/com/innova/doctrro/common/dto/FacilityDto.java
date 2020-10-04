package com.innova.doctrro.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.List;

public class FacilityDto {

    private FacilityDto() {
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class FacilityDtoRequest {
        @NotNull
        private String name;
        @NotNull
        private String type;
        @NotNull
        private List<Practitioner> doctors;
        private Admin admin;
        @NotNull
        @Min(-90)
        @Max(90)
        private double latitude;
        @NotNull
        @Min(-180)
        @Max(180)
        private double longitude;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FacilityDtoResponse {
        private String id;
        private String name;
        private String type;
        private List<Practitioner> doctors;
        private List<Admin> admins;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Practitioner {
        @NotNull
        private String regId;
        @NotNull
        private String name;
        @NotNull
        @Size(min = 1)
        private List<Slot> slots;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Slot {
            @NotNull
            private DayOfWeek dayOfWeek;
            @NotNull
            private String startTime;
            @NotNull
            private String endTime;
            @NotNull
            private byte duration;
            @NotNull
            private boolean isAutoApproveEnabled;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Admin implements Serializable {
        private String email;
        private String name;
    }
}
