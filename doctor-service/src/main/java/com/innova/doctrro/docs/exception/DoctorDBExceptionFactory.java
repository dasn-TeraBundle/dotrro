package com.innova.doctrro.docs.exception;

import com.innova.doctrro.common.constants.DBExceptionType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class DoctorDBExceptionFactory {

    public static RuntimeException createException(DBExceptionType dbExceptionType) {
        switch (dbExceptionType) {
            case DATA_NOT_FOUND:
                return new DoctorNotFoundException();
            case DUPLICATE_KEY:
                return new DuplicateDoctorException();
            default:
                return new RuntimeException("Something went wrong");
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class DoctorNotFoundException extends RuntimeException {

        private DoctorNotFoundException() {
            super("Doctor Not Found");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class DuplicateDoctorException extends RuntimeException {

        private DuplicateDoctorException() {
            super("Doctor already exists. You may consider updating it");
        }
    }
}
