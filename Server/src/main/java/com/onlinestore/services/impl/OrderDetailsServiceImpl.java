package com.onlinestore.services.impl;

import com.onlinestore.entities.OrderDetails;
import com.onlinestore.payload.CartDetailDto;
import com.onlinestore.payload.CartHelp;
import com.onlinestore.payload.OrderDetailDto;
import com.onlinestore.repositories.OrderDetailsRepo;
import com.onlinestore.repositories.CartRepo;
import com.onlinestore.repositories.UserRepo;
import com.onlinestore.services.OrderDetailsService;
import com.onlinestore.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDetailDto createOrderDetail(CartDetailDto cartDetailDto, Integer orderId) {
        OrderDetails orderDetail = this.modelMapper.map(cartDetailDto, OrderDetails.class);
        orderDetail.setOrder(this.orderService.findById(orderId));
        OrderDetails savedOrderDetail = this.orderDetailsRepo.save(orderDetail);
        return this.modelMapper.map(savedOrderDetail, OrderDetailDto.class);
    };

}
