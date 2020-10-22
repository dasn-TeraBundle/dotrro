package com.innova.doctrro.ps.service;

import com.innova.doctrro.ps.beans.Payment;
import static com.innova.doctrro.ps.dto.PaymentDto.*;

public class PaymentConverter {

    private PaymentConverter() { }

    public static Payment convert(PaymentDtoRequest request) {
        var payment = new Payment();
        payment.setBookingId(request.getBookingId());
        payment.setSlotId(request.getSlotId());
        payment.setPaymentGateway(request.getPaymentGateway());
        payment.setPaymentMode(request.getPaymentMode());
        payment.setPromo(request.getPromo());

        return payment;
    }

    public static PaymentDtoResponse convert(Payment payment) {
        return new PaymentDtoResponse();
    }
}
