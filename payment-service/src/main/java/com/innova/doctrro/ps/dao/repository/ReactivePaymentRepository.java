package com.innova.doctrro.ps.dao.repository;

import com.innova.doctrro.ps.beans.Payment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReactivePaymentRepository extends ReactiveMongoRepository<Payment, String> {
}
