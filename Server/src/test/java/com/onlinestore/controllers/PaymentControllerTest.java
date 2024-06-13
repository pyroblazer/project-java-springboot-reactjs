package com.onlinestore.controllers;

import com.onlinestore.services.CartService;
import com.onlinestore.services.OrderDetailsService;
import com.onlinestore.services.OrderService;
import com.onlinestore.services.PaymentService;
import com.onlinestore.services.UserService;
import com.stripe.exception.ApiException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    @Mock
    private OrderService orderService;

    @Mock
    private CartService cartService;

    @Mock
    private OrderDetailsService orderDetailsService;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessPayment_Success() throws Exception {
        PaymentIntent paymentIntent = mock(PaymentIntent.class);
        when(paymentIntent.getStatus()).thenReturn("succeeded");
        when(paymentService.createPaymentIntent(any(), any(), any())).thenReturn(paymentIntent);

        // Mocking the request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("amount", 1000);
        requestBody.put("id", "pm_12345");
        requestBody.put("email", "test@example.com");
        requestBody.put("name", "John Doe");

        ResponseEntity<Map<String, Object>> responseEntity = paymentController.processPayment(requestBody);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Payment successful", responseEntity.getBody().get("message"));
        assertEquals(true, responseEntity.getBody().get("success"));
        verify(paymentService, times(1)).processOrder("test@example.com");
    }

    @Test
    public void testProcessPayment_Failure() throws Exception {
        StripeException stripeException = mock(StripeException.class);
        when(stripeException.getMessage()).thenReturn("Payment failed");
        when(paymentService.createPaymentIntent(any(), any(), any())).thenThrow(stripeException);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", "Payment failed: Payment failed");
        responseMap.put("success", false);
        when(paymentService.handleStripeException(any(StripeException.class))).thenReturn(responseMap);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("amount", 1000);
        requestBody.put("id", "pm_12345");
        requestBody.put("email", "test@example.com");
        requestBody.put("name", "John Doe");

        ResponseEntity<Map<String, Object>> responseEntity = paymentController.processPayment(requestBody);

        System.out.println(responseEntity);

        // Asserting the response
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Payment failed: Payment failed", responseEntity.getBody().get("message"));
        assertEquals(false, responseEntity.getBody().get("success"));
    }
}