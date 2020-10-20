package com.innova.doctrro.bs.exception;

import com.innova.doctrro.common.constants.DBExceptionType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public final class BookingDBExceptionFactory {

    private BookingDBExceptionFactory() { }

    public static RuntimeException createException(DBExceptionType dbExceptionType) {
        if (dbExceptionType == DBExceptionType.DATA_NOT_FOUND) {
            return new BookingNotFoundException();
        }
        return new RuntimeException("Something went wrong");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class BookingNotFoundException extends RuntimeException {

        private BookingNotFoundException() {
            super("Booking Not Found");
        }
    }
}
