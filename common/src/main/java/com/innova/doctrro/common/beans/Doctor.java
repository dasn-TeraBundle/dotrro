package com.innova.doctrro.common.beans;

import com.innova.doctrro.common.constants.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Document(collection = "doctors")
public class Doctor implements Serializable {

    @Id
    private String regId;
    @Indexed
    private Set<String> emails;
    private String name;
    private Personal personal;
    private About about;
    private Date createdDate;

    public Doctor(String regId, String email, String name, Personal personal, About about) {
        this.regId = regId;
        this.addEmail(email);
        this.name = name;
        this.personal = personal;
        this.about = about;
        this.createdDate = new Date();
    }

    public Doctor(String regId, Set<String> emails, String name, Personal personal, About about) {
        this.regId = regId;
        this.emails = emails;
        this.name = name;
        this.personal = personal;
        this.about = about;
    }

    public void addEmail(String email) {
        if (this.emails == null)
            this.emails = new HashSet<>();
        emails.add(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Doctor doctor = (Doctor) o;

        return regId.equals(doctor.regId);
    }

    @Override
    public int hashCode() {
        return regId.hashCode();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Personal implements Serializable {
        private String dob;
        private Gender sex;

        public Personal(String dob, String sex) {
            this.dob = dob;
            this.sex = Gender.valueOf(sex);
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class About {
        private float experience;
        private String degree;
        private String speciality;
        private String about;

        public About(float experience, String degree, String speciality, String about) {
            this.experience = experience;
            this.degree = degree;
            this.speciality = speciality;
            this.about = about;
        }
    }
}
