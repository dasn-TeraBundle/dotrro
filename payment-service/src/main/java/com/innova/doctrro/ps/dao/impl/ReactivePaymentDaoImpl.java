package com.innova.doctrro.ps.dao.impl;

import com.innova.doctrro.ps.beans.Payment;
import com.innova.doctrro.ps.dao.ReactivePaymentDao;
import com.innova.doctrro.ps.dao.repository.ReactivePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;


@Component
public class ReactivePaymentDaoImpl implements ReactivePaymentDao {

    private final ReactivePaymentRepository paymentRepository;

    @Autowired
    public ReactivePaymentDaoImpl(ReactivePaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Mono<Payment> create(Payment item) {
        return paymentRepository.insert(item);
    }

    @Override
    public Mono<Payment> findById(String s) {
        return paymentRepository.findById(s);
    }

    @Override
    public Flux<Payment> findAll() {
        return Flux.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Payment> update(String s, Payment item) {
        return paymentRepository.save(item);
    }

    @Override
    public Mono<Void> remove(Payment item) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove() {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }
}
