package com.onlinestore.services;

import com.onlinestore.payload.CartDetailDto;
import com.onlinestore.payload.CartHelp;
import com.onlinestore.payload.OrderDetailDto;

public interface OrderDetailsService {
    public OrderDetailDto createOrderDetail(CartDetailDto cartDetailDto, Integer orderId);
}
