package com.onlinestore.controllers;

import com.onlinestore.payload.PaymentDetails;
import com.onlinestore.payload.PaymentInfoRequest;
import com.onlinestore.services.PaymentService;
import com.onlinestore.utils.ExtractJWT;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

//    @Autowired
//    public PaymentController(PaymentService paymentService) {
//        this.paymentService = paymentService;
//    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest)
            throws StripeException {

        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfoRequest);
        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    @PutMapping("/payment-complete")
    public ResponseEntity<String> stripePaymentComplete(@RequestHeader(value="Authorization") String token)
            throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        return paymentService.stripePayment(userEmail);
    }

    @GetMapping("/{amount}")
    public ResponseEntity<PaymentDetails> CreatePayment(@PathVariable Double amount) {
        String currency = "USD";
        PaymentDetails paymentDetails = this.paymentService.CreateOrder(amount, currency);
        return new ResponseEntity<>(paymentDetails, HttpStatusCode.valueOf(200));
    }
}