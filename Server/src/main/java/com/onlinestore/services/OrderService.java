package com.onlinestore.services;

import com.onlinestore.entities.Order;
import com.onlinestore.payload.CartDto;
import com.onlinestore.payload.CartHelp;
import com.onlinestore.payload.OrderDto;
import com.onlinestore.payload.OrderHelp;

import java.util.List;

public interface OrderService {
    public OrderDto createOrder(OrderDto orderDto);
    public List<Order> findAll();
    public List<OrderDto> getAllByUserId(int id);
    public List<OrderDto> getAllByUserEmail(String userEmail);
    public Order findById(int id);
    public OrderDto updateOrder(OrderDto orderDto, int orderId);
    OrderDto addProductToOrder(OrderHelp orderHelp, int orderId);
    void deleteOrder(int orderId);
}
