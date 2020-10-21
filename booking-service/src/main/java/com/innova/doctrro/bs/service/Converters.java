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
            resp.setId(booking.getId());
            resp.setBookedBy(new User(booking.getBookedBy().getEmail(), booking.getBookedBy().getName()));
            resp.setFacility(new BookingDtoResponse.Facility(booking.getFacility().getId(), booking.getFacility().getName()));
            resp.setPractioner(new BookingDtoResponse.Practitioner(booking.getPractioner().getRegId(), booking.getPractioner().getName()));
            resp.setSlotId(booking.getSlotId());
            resp.setAppointmentTime(booking.getStartTime());
            resp.setEndTime(booking.getEndTime());
            resp.setStatus(booking.getStatus());
            resp.setCost(booking.getCost());

            return resp;
        }
    }
}
