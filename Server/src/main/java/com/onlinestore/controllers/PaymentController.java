package com.onlinestore.controllers;

import com.onlinestore.services.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${stripe.key.secret}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long amount = Long.valueOf((Integer) requestBody.get("amount"));
            String paymentMethodId = (String) requestBody.get("id");
            String email = (String) requestBody.get("email");

            PaymentIntent payment = paymentService.createPaymentIntent(amount, paymentMethodId, email);
            response.put("message", "Payment successful");
            response.put("success", true);

            paymentService.processOrder(email);

            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body(paymentService.handleStripeException(e));
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during payment processing", e);
        }
    }
}