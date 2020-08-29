package com.innova.doctrro.ps.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PatientException extends RuntimeException {

    public PatientException(String message) {
        super(message);
    }
}
