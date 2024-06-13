package com.onlinestore.services.impl;


import com.onlinestore.entities.Payment;
import com.onlinestore.payload.PaymentDetails;
import com.onlinestore.payload.PaymentInfoRequest;
import com.onlinestore.repositories.PaymentRepo;
import com.onlinestore.services.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepo paymentRepository;

    @Autowired
    public void PaymentService(PaymentRepo paymentRepository, @Value("${stripe.key.secret}") String secretKey) {
        this.paymentRepository = paymentRepository;
        Stripe.apiKey = secretKey;
    }

    public PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws StripeException, StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(paymentInfoRequest.getAmount())
                .setCurrency(paymentInfoRequest.getCurrency())
                .setDescription("Online Store")
                .setPaymentMethod(paymentInfoRequest.getId())
                .setConfirm(true)
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER)
                                .build()
                )
                .addAllPaymentMethodType(paymentMethodTypes)
                .build();

        return PaymentIntent.create(params);
    }


    public ResponseEntity<String> stripePayment(String userEmail) throws Exception {
        Payment payment = paymentRepository.findByUserEmail(userEmail);

        if (payment == null) {
            throw new Exception("Payment information is missing");
        }
        payment.setAmount(00.00);
        paymentRepository.save(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public PaymentDetails CreateOrder(Double amount, String currency) {
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("amount",amount*100.0);
            jsonObject.put("currency",currency);
            System.out.println(jsonObject);

            String orderId="1";
            String key = "1";

            PaymentDetails paymentDetails=new PaymentDetails(orderId,amount,currency,key);
            return paymentDetails;

        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

}
