package com.innova.doctrro.ps.beans;


import com.innova.doctrro.common.constants.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Document(collection = "payments")
public class Payment {

    @Id
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

    @CreatedDate
    private LocalDateTime createdOn;
    @LastModifiedDate
    private LocalDateTime updatedOn;
    @Version
    private long version;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class User {
        private String email;
        private String name;
    }
}
