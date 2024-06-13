package com.onlinestore.services.impl;

import com.onlinestore.entities.*;
import com.onlinestore.payload.*;
import com.onlinestore.repositories.OrderRepo;
import com.onlinestore.repositories.ProductRepo;
import com.onlinestore.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private ProductRepo productRepo;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {
        OrderDto orderDto = new OrderDto();
        UserDto userDto = new UserDto();
        userDto.setId(1);
        orderDto.setUser(userDto);

        User user = new User();
        user.setId(1);

        Order order = new Order();
        order.setId(1);
        order.setUser(user);

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(orderRepo.save(any(Order.class))).thenReturn(order);
        when(modelMapper.map(order, OrderDto.class)).thenReturn(orderDto);

        OrderDto result = orderService.createOrder(orderDto);

        assertNotNull(result);
        assertEquals(1, result.getUser().getId());
        verify(orderRepo).save(any(Order.class));
    }

    @Test
    void testFindAll() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());

        when(orderRepo.findAll()).thenReturn(orders);

        List<Order> result = orderService.findAll();

        assertEquals(2, result.size());
        verify(orderRepo).findAll();
    }

    @Test
    void testGetAllByUserId() {
        int userId = 1;
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());

        when(orderRepo.findOrdersByUserId(userId)).thenReturn(orders);
        when(modelMapper.map(any(Order.class), eq(OrderDto.class))).thenReturn(new OrderDto());

        List<OrderDto> result = orderService.getAllByUserId(userId);

        assertEquals(2, result.size());
        verify(orderRepo).findOrdersByUserId(userId);
    }

    @Test
    void testGetAllByUserEmail() {
        String userEmail = "user@example.com";
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());

        when(orderRepo.findOrdersByUserEmail(userEmail)).thenReturn(orders);
        when(modelMapper.map(any(Order.class), eq(OrderDto.class))).thenReturn(new OrderDto());

        List<OrderDto> result = orderService.getAllByUserEmail(userEmail);

        assertEquals(2, result.size());
        verify(orderRepo).findOrdersByUserEmail(userEmail);
    }

    @Test
    void testFindById() {
        int orderId = 1;
        Order order = new Order();
        order.setId(orderId);

        when(orderRepo.findById(orderId)).thenReturn(Optional.of(order));

        Order result = orderService.findById(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
        verify(orderRepo).findById(orderId);
    }

    @Test
    void testUpdateOrder() {
        int orderId = 1;
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderId);
        orderDto.setOrderStatus(2);
        orderDto.setMoment(Instant.now());

        Order existingOrder = new Order();
        existingOrder.setId(orderId);

        when(orderRepo.findById(orderId)).thenReturn(Optional.of(existingOrder));
        when(orderRepo.save(any(Order.class))).thenReturn(existingOrder);
        when(modelMapper.map(existingOrder, OrderDto.class)).thenReturn(orderDto);

        OrderDto result = orderService.updateOrder(orderDto, orderId);

        assertNotNull(result);
        assertEquals(2, result.getOrderStatus());
        verify(orderRepo).save(any(Order.class));
    }

    @Test
    void testDeleteOrder() {
        int orderId = 1;

        orderService.deleteOrder(orderId);

        verify(orderRepo).deleteById(orderId);
    }

    @Test
    void testAddProductToOrder() {
        int orderId = 1;
        OrderHelp orderHelp = new OrderHelp();
        orderHelp.setProductId(1);
        orderHelp.setQuantity(2);
        orderHelp.setUserId(1);

        User user = new User();
        user.setId(1);

        Product product = new Product();
        product.setId(1);
        product.setPrice(10.0);

        Order existingOrder = new Order();
        existingOrder.setId(orderId);
        existingOrder.setUser(user);
        existingOrder.setOrderDetails(new ArrayList<>());

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        when(orderRepo.findById(orderId)).thenReturn(Optional.of(existingOrder));
        when(orderRepo.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);

        OrderDto result = orderService.addProductToOrder(orderHelp, orderId);

        assertNotNull(result);
        verify(orderRepo).save(any(Order.class));
        assertEquals(1, result.getOrderDetails().size());
        assertEquals(20.0, result.getTotalAmount(), 0.001);
    }
}