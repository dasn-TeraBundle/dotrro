package com.innova.doctrro.bs.exception;

import com.innova.doctrro.common.constants.DBExceptionType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public final class BookingSlotDBExceptionFactory {

    private BookingSlotDBExceptionFactory() { }

    public static RuntimeException createException(DBExceptionType dbExceptionType) {
        switch (dbExceptionType) {
            case DATA_NOT_FOUND:
                return new BookingSlotNotFoundException();
            case DUPLICATE_KEY:
                return new DuplicateSlotException();
            case OPTIMISTIC_LOCKING_FAILURE:
                return new OptimisticSlotLockingException();
            default:
                return new RuntimeException("Something went wrong");
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class BookingSlotNotFoundException extends RuntimeException {

        private BookingSlotNotFoundException() {
            super("Slot Not Found");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class DuplicateSlotException extends RuntimeException {

        private DuplicateSlotException() {
            super("Slot already exists. You may consider updating it");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class OptimisticSlotLockingException extends RuntimeException {

        private OptimisticSlotLockingException() {
            super("Slot currently unavailable");
        }
    }

}
