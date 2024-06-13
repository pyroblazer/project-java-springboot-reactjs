package com.onlinestore.services.impl;

import com.onlinestore.entities.Order;
import com.onlinestore.entities.OrderDetails;
import com.onlinestore.payload.CartDetailDto;
import com.onlinestore.payload.OrderDetailDto;
import com.onlinestore.repositories.OrderDetailsRepo;
import com.onlinestore.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class OrderDetailsServiceImplTest {

    @InjectMocks
    private OrderDetailsServiceImpl orderDetailsService;

    @Mock
    private OrderDetailsRepo orderDetailsRepo;

    @Mock
    private OrderService orderService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrderDetail() {
        CartDetailDto cartDetailDto = new CartDetailDto();
        Order order = new Order();
        OrderDetails orderDetails = new OrderDetails();
        OrderDetailDto orderDetailDto = new OrderDetailDto();

        when(orderService.findById(anyInt())).thenReturn(order);
        when(modelMapper.map(any(CartDetailDto.class), eq(OrderDetails.class))).thenReturn(orderDetails);
        when(orderDetailsRepo.save(any(OrderDetails.class))).thenReturn(orderDetails);
        when(modelMapper.map(any(OrderDetails.class), eq(OrderDetailDto.class))).thenReturn(orderDetailDto);

        OrderDetailDto result = orderDetailsService.createOrderDetail(cartDetailDto, 1);

        verify(orderService, times(1)).findById(1);
        verify(orderDetailsRepo, times(1)).save(orderDetails);
        verify(modelMapper, times(1)).map(orderDetails, OrderDetailDto.class);

        assertNotNull(result);
    }
}