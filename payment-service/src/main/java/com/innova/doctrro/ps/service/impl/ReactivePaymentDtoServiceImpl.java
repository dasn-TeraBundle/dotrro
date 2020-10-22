package com.innova.doctrro.ps.service.impl;


import com.innova.doctrro.ps.dao.ReactivePaymentDao;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;
import static com.innova.doctrro.ps.dto.PaymentDto.*;
import com.innova.doctrro.ps.service.ReactivePaymentService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactivePaymentDtoServiceImpl implements ReactivePaymentService {

    private final ReactivePaymentDao paymentDao;

    public ReactivePaymentDtoServiceImpl(ReactivePaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @Override
    public Mono<PaymentDtoResponse> create(PaymentDtoRequest item) {
        return null;
    }

    @Override
    public Mono<PaymentDtoResponse> findById(String s) {
        return null;
    }

    @Override
    public Flux<PaymentDtoResponse> findAll() {
        return Flux.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<PaymentDtoResponse> update(String s, PaymentDtoRequest item) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove(String s) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove(PaymentDtoRequest item) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove() {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }
}
