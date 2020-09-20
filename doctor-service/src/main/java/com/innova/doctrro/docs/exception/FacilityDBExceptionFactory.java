package com.innova.doctrro.docs.exception;

import com.innova.doctrro.common.constants.DBExceptionType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public final class FacilityDBExceptionFactory {

    private FacilityDBExceptionFactory() { }

    public static RuntimeException createException(DBExceptionType dbExceptionType) {
        switch (dbExceptionType) {
            case DATA_NOT_FOUND:
                return new FacilityNotFoundException();
            case DUPLICATE_KEY:
                return new DuplicateFacilityException();
            default:
                return new RuntimeException("Something went wrong");
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class FacilityNotFoundException extends RuntimeException {

        private FacilityNotFoundException() {
            super("Facility Not Found");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class DuplicateFacilityException extends RuntimeException {

        private DuplicateFacilityException() {
            super("Facility already exists. You may consider updating it");
        }
    }
}
