package com.innova.doctrro.bs.beans;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;
    private User bookedBy;
    private String slotId;
    private Facility facility;
    private Practitioner practioner;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingStatus status;

    @Version
    private long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        if (!practioner.equals(booking.practioner)) return false;
        if (!startTime.equals(booking.startTime)) return false;
        return status == booking.status;
    }

    @Override
    public int hashCode() {
        int result = practioner.hashCode();
        result = 31 * result + startTime.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", version=" + version +
                '}';
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private String email;
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Practitioner {
        private String regId;
        private String name;

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
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Facility {
        private String id;
        private String name;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Facility facility = (Facility) o;

            return id.equals(facility.id);
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }
}
