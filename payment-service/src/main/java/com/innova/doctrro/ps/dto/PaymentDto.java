package com.innova.doctrro.ps.dto;

import com.innova.doctrro.common.constants.PaymentStatus;
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
        private String paymentGateway;
        @NotNull
        private String paymentMode;
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
    public static class User {
        private String email;
        private String name;
    }

}
