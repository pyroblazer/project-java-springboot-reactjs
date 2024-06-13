package com.onlinestore.services;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PaymentService {
    public PaymentIntent createPaymentIntent(Long amount, String paymentMethodId, String email) throws StripeException;
    public void processOrder(String email) throws Exception;
    public Map<String, Object> handleStripeException(StripeException e);
}