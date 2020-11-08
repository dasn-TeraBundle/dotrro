package com.innova.doctrro.ps.controller;


import com.innova.doctrro.ps.service.ReactivePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static com.innova.doctrro.ps.dto.PaymentDto.*;


@RestController
@RequestMapping("/payment-service")
public class PaymentController {

    private final ReactivePaymentService paymentService;

    @Autowired
    public PaymentController(ReactivePaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/")
    public Mono<PaymentDtoResponse> initiate(Mono<BearerTokenAuthentication> auth,
                                             @RequestBody @Valid PaymentDtoRequest request) {
        return auth
                .map(BearerTokenAuthentication::getTokenAttributes)
                .flatMap(details -> {
                    request.setPaidBy(new User(details.get("email").toString(), details.get("name").toString()));
                    return paymentService.create(request);
                });
    }

    @PatchMapping("/{paymentId}")
    public Mono<PaymentDtoResponse> update(Mono<BearerTokenAuthentication> auth,
                                             @PathVariable String paymentId,
                                             @RequestBody @Valid PaymentChargeRequest request) {
        return auth
                .map(BearerTokenAuthentication::getTokenAttributes)
                .flatMap(details -> paymentService.update(paymentId, request));
    }

}
