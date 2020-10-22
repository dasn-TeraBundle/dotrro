package com.innova.doctrro.ps.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BookingDtoResponse implements Serializable {
    private String id;
    private String slotId;
    private Facility facility;
    private Practitioner practioner;
    private LocalDateTime appointmentTime;
    private double cost;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Facility implements Serializable {
        private String id;
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Practitioner implements Serializable {
        private String regId;
        private String name;
    }
}
