package com.onlinestore.services.impl;


//import com.onlinestore.payload.PaymentDetails;
import com.onlinestore.payload.*;
        import com.onlinestore.services.*;
        import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private UserService userService;

    public PaymentIntent createPaymentIntent(Long amount, String paymentMethodId, String email) throws StripeException {
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

        return PaymentIntent.create(createParams);
    }

    @Override
    @Transactional
    public void processOrder(String email) throws Exception {
        CartDto cartDto = cartService.getCart(email);
        List<CartDetailDto> cartDetailDtos = cartDto.getCartDetails();
        List<OrderDetailDto> orderDetailDtos = new ArrayList<>();

        OrderDto orderDto = new OrderDto();
        UserDto user = cartDto.getUser();
        orderDto.setUser(user);
        orderDto.setOrderDetails(orderDetailDtos);

        orderDto.setTotalAmount(cartDto.getTotalAmount());

        OrderDto savedOrderDto = orderService.createOrder(orderDto);

        for (CartDetailDto cartDetailDto : cartDetailDtos) {
            OrderDetailDto orderDetailDto = orderDetailsService.createOrderDetail(cartDetailDto, savedOrderDto.getId());
            orderDetailDtos.add(orderDetailDto);
        }

        cartService.emptyCart(cartDto.getId());
    }

    public Map<String, Object> handleStripeException(StripeException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Payment failed: " + e.getMessage());
        response.put("success", false);
        return response;
    }
}
