package com.innova.doctrro.ps.service;

import com.innova.doctrro.common.service.ReactiveGenericService;
import reactor.core.publisher.Mono;

import static com.innova.doctrro.ps.dto.PaymentDto.*;

public interface ReactivePaymentService extends ReactiveGenericService<PaymentDtoRequest, PaymentDtoResponse, String> {

    //    Mono<PaymentDtoResponse> initiatePaymentCash(String paymentId);
//    Mono<PaymentDtoResponse> initiatePaymentStripe(String paymentId, String token);
    Mono<PaymentDtoResponse> update(String paymentId, PaymentChargeRequest request);
}
