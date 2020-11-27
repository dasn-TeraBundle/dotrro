package com.innova.doctrro.ps.service.impl;


import com.innova.doctrro.common.constants.PaymentStatus;
import com.innova.doctrro.common.exception.InvalidInputException;
import com.innova.doctrro.ps.beans.Payment;
import com.innova.doctrro.ps.dao.ReactivePaymentDao;
import com.innova.doctrro.ps.dto.BookingDtoResponse;
import com.innova.doctrro.ps.service.BookingServiceClient;
import com.innova.doctrro.ps.service.PaymentConverter;
import com.innova.doctrro.ps.service.ReactivePaymentService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import feign.FeignException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.SignatureException;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;
import static com.innova.doctrro.ps.dto.PaymentDto.*;

@Service
public class ReactivePaymentDtoServiceImpl implements ReactivePaymentService {

    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    private final ReactivePaymentDao paymentDao;
    private final BookingServiceClient bookingServiceClient;

    private final RazorpayClient razorpayClient;

    @Autowired
    public ReactivePaymentDtoServiceImpl(ReactivePaymentDao paymentDao, BookingServiceClient bookingServiceClient) {
        this.paymentDao = paymentDao;
        this.bookingServiceClient = bookingServiceClient;
//        Stripe.apiKey = "sk_test_gX1tNajU8wgn69CNY1cvOjPn";
        try {
            razorpayClient = new RazorpayClient("rzp_test_ShZR1A8VnnfG4G", "C0boXioECGzqSSC7PBpf5fiM");
        } catch (RazorpayException e) {
            throw new IllegalArgumentException("Razorpay can't be initialized");
        }

    }

    private static String getHash(String data) throws SignatureException {
        String result;
        try {
            SecretKeySpec signingKey = new SecretKeySpec("C0boXioECGzqSSC7PBpf5fiM".getBytes(), HMAC_SHA256_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());

            result = DatatypeConverter.printHexBinary(rawHmac).toLowerCase();

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }

    @Override
    public Mono<PaymentDtoResponse> create(PaymentDtoRequest item) {
        Payment payment = PaymentConverter.convert(item);

        return bookingServiceClient.find(item.getBookingId())
                .flatMap(booking -> {
                    payment.setCost(booking.getCost());
                    payment.setFinalCost(payment.getCost() - payment.getDiscount());
                    payment.setStatus(PaymentStatus.INITIATED);

                    switch (item.getPaymentGateway()) {
                        case RAZORPAY:
                            try {
                                createRazorpayOrder(payment, booking);
                            } catch (RazorpayException e) {
                                return Mono.error(new RuntimeException(""));
                            }
                            break;
                        case CASH:
                            break;
//                        case STRIPE:
//                            try {
//                                createStripeOrder(payment);
//                            } catch (StripeException e) {
//                                return Mono.error(new RuntimeException(""));
//                            }
                        default:
                            break;

                    }

                    return paymentDao.create(payment);
                })
                .map(PaymentConverter::convert)
                .onErrorMap(err -> {
                    if (err instanceof FeignException.NotFound) {
                        return new InvalidInputException("Invalid input");
                    }

                    return err;
                });
    }

//    public Mono<PaymentDtoResponse> initiatePaymentStripe(String paymentId, String token) {
//        return paymentDao.findById(paymentId)
//                .flatMap(payment -> {
//                    switch (payment.getPaymentGateway()) {
//                        case STRIPE:
//                            long amt = (long) (payment.getFinalCost() * 100);
//                            ChargeCreateParams params = ChargeCreateParams.builder()
//                                    .setAmount(amt)
//                                    .setCurrency("inr")
//                                    .setSource(token)
//                                    .setDescription("Booking - " + payment.getBookingId() + " Stripe Order - " + payment.getPaymentGatewayOrderId())
//                                    .setReceiptEmail(payment.getPaidBy().getEmail())
//                                    .build();
//                            Charge charge = null;
//
//                            try {
//                                charge = Charge.create(params);
//                                payment.setStatus(PaymentStatus.valueOf(charge.getStatus().toUpperCase()));
//                            } catch (StripeException e) {
//                                e.printStackTrace();
//                            }
//
//                            return paymentDao.update(paymentId, payment);
//                        case CASH:
//                            payment.setStatus(PaymentStatus.SUCCEDED);
//                            return paymentDao.update(paymentId, payment);
//                        default:
//                            return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
//                    }
//                }).map(PaymentConverter::convert);
//
//    }

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
    public Mono<PaymentDtoResponse> update(String paymentId, PaymentChargeRequest request) {
        switch (request.getGateway()) {
            case CASH:
                return initiatePaymentCash(paymentId);
            case RAZORPAY:
                return initiatePaymentRazorpay(paymentId, request);
            default:
                return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
        }
    }

    @Override
    public Mono<Void> remove(String s) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    @Override
    public Mono<Void> remove() {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

//    private void createStripeOrder(Payment payment) throws StripeException {
////        List<Object> items = new ArrayList<>();
////        Map<String, Object> item1 = new HashMap<>();
////        item1.put("type", "sku");
////        item1.put("parent", "sku_IFvLpbKgn0jQSn");
////        items.add(item1);
////        Map<String, Object> address = new HashMap<>();
////        address.put("line1", "1234 Main Street");
////        address.put("city", "San Francisco");
////        address.put("state", "CA");
////        address.put("country", "US");
////        address.put("postal_code", "94111");
////        Map<String, Object> shipping = new HashMap<>();
////        shipping.put("name", "Jenny Rosen");
////        shipping.put("address", address);
////        Map<String, Object> params = new HashMap<>();
////        params.put("currency", "usd");
////        params.put("email", "jenny.rosen@example.com");
////        params.put("items", items);
////        params.put("shipping", shipping);
//
//
//        OrderCreateParams.Item item1 = OrderCreateParams.Item.builder()
//                .setCurrency("inr")
//                .setAmount((long) (payment.getFinalCost() * 100))
//                .build();
//        OrderCreateParams params = OrderCreateParams.builder()
//                .setCurrency("inr")
//                .addItem(item1)
//                .build();
//
//        Order order = Order.create(params);
//        payment.setPaymentGatewayOrderId(order.getId());
//    }

    private void createRazorpayOrder(Payment payment, BookingDtoResponse booking) throws RazorpayException {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (long) (payment.getFinalCost() * 100));
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", booking.getId());

        var razorpayOrder = razorpayClient.Orders.create(orderRequest);
        payment.setPaymentGatewayOrderId(razorpayOrder.get("id"));
    }

    private Mono<PaymentDtoResponse> initiatePaymentCash(String paymentId) {
        return paymentDao.findById(paymentId)
                .flatMap(payment -> {
                    payment.setStatus(PaymentStatus.SUCCEDED);

                    return paymentDao.update(paymentId, payment);
                }).map(PaymentConverter::convert);
    }

    private Mono<PaymentDtoResponse> initiatePaymentRazorpay(String paymentId, PaymentChargeRequest request) {
        return paymentDao.findById(paymentId)
                .flatMap(payment -> {
                    payment.setReferenceNo(request.getRazorpayPaymentId());

                    try {
                        String data = payment.getPaymentGatewayOrderId() + "|" + request.getRazorpayPaymentId();
                        String generated_signature = getHash(data);
                        if (request.getRazorpaySignature().equals(generated_signature)) {
                            payment.setStatus(PaymentStatus.SUCCEDED);
                        } else {
                            payment.setStatus(PaymentStatus.PENDING);
                        }
                    } catch (SignatureException e) {
                        payment.setStatus(PaymentStatus.PENDING);
                    }

                    return paymentDao.update(paymentId, payment);
                }).map(PaymentConverter::convert);
    }

}
