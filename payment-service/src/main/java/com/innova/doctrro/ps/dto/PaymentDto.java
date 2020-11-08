package com.innova.doctrro.ps.dto;

import com.innova.doctrro.common.constants.PaymentGateway;
import com.innova.doctrro.common.constants.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class PaymentDto {

    private PaymentDto() { }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PaymentDtoRequest {
        @NotNull
        private String bookingId;
        @NotNull
        private String slotId;
        @NotNull
        private PaymentGateway paymentGateway;
//        @NotNull
//        private String paymentMode;
//        private double cost;
        private String promo;
//        private double discount;
//        private double finalCost;
//        private PaymentStatus status;
        private User paidBy;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PaymentDtoResponse {
        private String id;
        private String bookingId;
        private String slotId;
        private String paymentGateway;
        private String paymentMode;
        private String referenceNo;
        private double cost;
        private String promo;
        private double discount;
        private double finalCost;
        private PaymentStatus status;
        private User paidBy;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PaymentChargeRequest {
        @NotNull
        private String id;
        @NotNull
        private String bookingId;
        @NotNull
        private PaymentGateway gateway;
        private String razorpayPaymentId;
        private String razorpayOrderId;
        private String razorpaySignature;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private String email;
        private String name;
    }

}
