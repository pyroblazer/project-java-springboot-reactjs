package com.onlinestore.services.impl;

import com.onlinestore.payload.*;
import com.onlinestore.services.*;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceImplTest {

    @Mock
    private OrderService orderService;

    @Mock
    private CartService cartService;

    @Mock
    private OrderDetailsService orderDetailsService;

    @Mock
    private UserService userService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private CartDto cartDto;
    private OrderDto orderDto;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userDto = UserDto.builder().id(1).name("Test User").build();

        CartDetailDto cartDetailDto = CartDetailDto.builder().id(1).quantity(2).amount(200.0).build();
        cartDto = CartDto.builder().id(1).user(userDto).totalAmount(400.0).cartDetails(Collections.singletonList(cartDetailDto)).build();

        orderDto = OrderDto.builder().id(1).user(userDto).orderDetails(List.of(new OrderDetailDto())).build();
    }

    @Test
    public void testCreatePaymentIntent() throws StripeException {
        Long amount = 100L;
        String paymentMethodId = "pm_1JY7dP2eZvKYlo2CxYjqFv5k";
        String email = "test@example.com";

        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setAmount(amount)
                .setCurrency("USD")
                .setDescription("Online Store")
                .setConfirm(true)
                .setPaymentMethod(paymentMethodId)
                .setAutomaticPaymentMethods(
                        new PaymentIntentCreateParams.AutomaticPaymentMethods.Builder()
                                .setEnabled(true)
                                .setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER)
                                .build())
                .setReceiptEmail(email)
                .build();

        PaymentIntent expectedPaymentIntent = mock(PaymentIntent.class);
        when(expectedPaymentIntent.getAmount()).thenReturn(amount);
        when(expectedPaymentIntent.getCurrency()).thenReturn("usd");

        mockStatic(PaymentIntent.class);
        when(PaymentIntent.create(any(PaymentIntentCreateParams.class))).thenReturn(expectedPaymentIntent);

        PaymentIntent paymentIntent = paymentService.createPaymentIntent(amount, paymentMethodId, email);

        assertEquals(expectedPaymentIntent.getAmount(), paymentIntent.getAmount());
        assertEquals(expectedPaymentIntent.getCurrency(), paymentIntent.getCurrency());
    }

    @Test
    public void testProcessOrder() throws Exception {
        when(cartService.getCart(anyString())).thenReturn(cartDto);
        when(orderService.createOrder(any(OrderDto.class))).thenReturn(orderDto);
        when(orderDetailsService.createOrderDetail(any(CartDetailDto.class), anyInt())).thenReturn(new OrderDetailDto());

        paymentService.processOrder("test@example.com");

        verify(cartService, times(1)).emptyCart(cartDto.getId());
        verify(orderService, times(1)).createOrder(any(OrderDto.class));
        verify(orderDetailsService, times(1)).createOrderDetail(any(CartDetailDto.class), anyInt());
    }

    @Test
    public void testHandleStripeException() {
        StripeException stripeException = mock(StripeException.class);
        when(stripeException.getMessage()).thenReturn("Payment failed");

        Map<String, Object> response = paymentService.handleStripeException(stripeException);

        assertFalse((Boolean) response.get("success"));
        assertEquals("Payment failed: Payment failed", response.get("message"));
    }
}