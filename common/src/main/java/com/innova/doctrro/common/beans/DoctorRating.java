package com.innova.doctrro.common.beans;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "doctor_ratings")
public class DoctorRating {

    @Id
    private String id;
    private RatedDoctor doctor;
    private User ratedBy;
    private byte rating;
    private String comment;
    private Date createdDate;


    public DoctorRating(RatedDoctor doctor, User ratedBy, byte rating, String comment) {
        this.doctor = doctor;
        this.ratedBy = ratedBy;
        this.rating = rating;
        this.comment = comment;
        this.createdDate = new Date();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RatedDoctor {
        private String regId;
        private String name;

        public RatedDoctor(String regId, String name) {
            this.regId = regId;
            this.name = name;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class User {
        private String email;
        private String name;

        public User(String email, String name) {
            this.email = email;
            this.name = name;
        }
    }
}
