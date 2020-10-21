package com.innova.doctrro.common.beans;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Document(collection = "facilities")
public class Facility implements Serializable {

    @Id
    private String id;
    private String name;
    private String type;
    private List<Practitioner> doctors;
    private List<Admin> admins;
    private Location location;

    public Facility(String name, String type, List<Practitioner> doctors, List<Admin> admins, Location location) {
        this.name = name;
        this.type = type;
        this.doctors = doctors;
        this.admins = admins;
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Facility facility = (Facility) o;

        if (!id.equals(facility.id)) return false;
        if (!name.equals(facility.name)) return false;
        if (!type.equals(facility.type)) return false;
        return location.equals(facility.location);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Practitioner implements Serializable {
        private String regId;
        private String name;
        private Set<Slot> slots;

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
        public static class Slot implements Serializable {
            private DayOfWeek dayOfWeek;
            private String startTime;
            private String endTime;
            private byte duration;
            private double fee;
            private boolean isAutoApproveEnabled;

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Slot slot = (Slot) o;

                if (dayOfWeek != slot.dayOfWeek) return false;
                if (!startTime.equals(slot.startTime)) return false;
                return endTime.equals(slot.endTime);
            }

            @Override
            public int hashCode() {
                int result = dayOfWeek.hashCode();
                result = 31 * result + startTime.hashCode();
                result = 31 * result + endTime.hashCode();
                return result;
            }
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Admin implements Serializable {
        private String email;
        private String name;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Admin admin = (Admin) o;

            return email.equals(admin.email);
        }

        @Override
        public int hashCode() {
            return email.hashCode();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location implements Serializable {
        private String type;
        private List<Double> coordinates;

        public Location(double longitude, double latitude) {
            this(Arrays.asList(longitude, latitude));
        }

        private Location(List<Double> coordinates) {
            this("Point", coordinates);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Location location = (Location) o;

            if (type != null ? !type.equals(location.type) : location.type != null) return false;
            return coordinates != null ? coordinates.equals(location.coordinates) : location.coordinates == null;
        }

        @Override
        public int hashCode() {
            int result = type != null ? type.hashCode() : 0;
            result = 31 * result + (coordinates != null ? coordinates.hashCode() : 0);
            return result;
        }
    }
}
