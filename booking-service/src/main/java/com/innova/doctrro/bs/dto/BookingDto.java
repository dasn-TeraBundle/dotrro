package com.innova.doctrro.bs.dto;

import com.innova.doctrro.bs.beans.Booking;
import com.innova.doctrro.bs.beans.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class BookingDto {

    private BookingDto() {
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class BookingDtoRequest {
        @NotNull
        private String slotId;
        private User bookedBy;
        @NotNull
        private String facilityId;
        @NotNull
        private String doctorId;
        @NotNull
        private LocalDateTime startTime;
        @NotNull
        private LocalDateTime endTime;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class BookingDtoResponse {
        private String id;
        private User bookedBy;
        private String slotId;
        private Facility facility;
        private Practitioner practioner;
        private LocalDateTime appointmentTime;
        @JsonIgnore
        private LocalDateTime endTime;
        private double cost;
        private BookingStatus status;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Facility {
            private String id;
            private String name;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Practitioner {
            private String regId;
            private String name;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private String email;
        private String name;
    }

}
