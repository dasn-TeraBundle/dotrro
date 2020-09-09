package com.innova.doctrro.docs.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateDoctorException extends RuntimeException {

    public DuplicateDoctorException() {
        super("Doctor already exists. You may consider updating it");
    }
}
