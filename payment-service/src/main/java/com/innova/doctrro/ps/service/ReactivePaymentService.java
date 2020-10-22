package com.innova.doctrro.ps.service;

import com.innova.doctrro.common.service.ReactiveGenericService;

import static com.innova.doctrro.ps.dto.PaymentDto.PaymentDtoRequest;
import static com.innova.doctrro.ps.dto.PaymentDto.PaymentDtoResponse;

public interface ReactivePaymentService extends ReactiveGenericService<PaymentDtoRequest, PaymentDtoResponse, String> {
}
