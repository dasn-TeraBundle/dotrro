package com.innova.doctrro.bs.service;

import com.innova.doctrro.bs.beans.Booking;
import static com.innova.doctrro.bs.dto.BookingDto.*;

public class Converters {

    private Converters() { }

    public static class BookingConverter {

        private BookingConverter() { }

        public static Booking convert(BookingDtoRequest req) {
            var booking = new Booking();
            booking.setSlotId(req.getSlotId());
            booking.setBookedBy(new Booking.User(req.getBookedBy().getEmail(), req.getBookedBy().getName()));
            booking.setFacility(new Booking.Facility(req.getFacilityId(), null));
            booking.setPractioner(new Booking.Practitioner(req.getDoctorId(), null));
            booking.setStartTime(req.getStartTime());
            booking.setEndTime(req.getEndTime());

            return booking;
        }

        public static BookingDtoResponse convert(Booking booking) {
            var resp = new BookingDtoResponse();

            return resp;
        }
    }
}
