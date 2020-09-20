package com.innova.doctrro.common.constants;

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
