package com.onlinestore.services;

import com.onlinestore.payload.PaymentDetails;
import com.onlinestore.payload.PaymentInfoRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws StripeException;
    ResponseEntity<String> stripePayment(String userEmail) throws Exception;
    public PaymentDetails CreateOrder(Double amount, String currency);
}