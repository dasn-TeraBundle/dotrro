package com.innova.doctrro.ss.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDtoResponse {
    private String id;
    private String name;
    private String type;
    private List<Practitioner> practitioners;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Practitioner {
        private String regId;
        private String name;
        private String sex;
        private float experience;
        private String degree;
        private String speciality;
        private List<Slot> slots;

        public Practitioner(String regId, String name) {
            this.regId = regId;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Practitioner that = (Practitioner) o;

            return regId.equals(that.regId);
        }

        @Override
        public int hashCode() {
            return regId.hashCode();
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Slot {
            private LocalDateTime startTime;
            private LocalDateTime endTime;
        }

    }
}
