package com.innova.doctrro.ps.beans;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "patients")
public class Patient {

    @Id
    private String email;
    private String name;
    private Personal personal;
    private Lifestyle lifestyle;
    private Medical medical;

    public Patient(String email, String name, Personal personal, Lifestyle lifestyle, Medical medical) {
        this.email = email;
        this.name = name;
        this.personal = personal;
        this.lifestyle = lifestyle;
        this.medical = medical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        return email.equals(patient.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Personal {
        private Gender sex;
        private String dob;
        private String bloodGroup;
        private String height;
        private String weight;

        public Personal(String sex, String dob, String bloodGroup, String height, String weight) {
            this.sex = Gender.valueOf(sex);
            this.dob = dob;
            this.bloodGroup = bloodGroup;
            this.height = height;
            this.weight = weight;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Lifestyle {
        private String smoke;
        private String alcohol;
        private String diet;
        private String activity;
        private String married;

        public Lifestyle(String smoke, String alcohol, String diet, String activity, String married) {
            this.smoke = smoke;
            this.alcohol = alcohol;
            this.diet = diet;
            this.activity = activity;
            this.married = married;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Medical {
        private Set<String> chronic;

        public Medical(Set<String> chronic) {
            this.chronic = chronic;
        }
    }

    public enum Gender {
        M("MALE"), F("FEMALE"), O("OTHERS");

        private final String value;

        Gender(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
