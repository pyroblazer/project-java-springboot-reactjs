package com.onlinestore.controllers;

import com.onlinestore.entities.Order;
import com.onlinestore.payload.*;
import com.onlinestore.services.CartService;
import com.onlinestore.services.OrderDetailsService;
import com.onlinestore.services.OrderService;
import com.onlinestore.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

@ActiveProfiles("test")
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    @Mock
    private OrderDetailsService orderDetailsService;

    @Mock
    private CartService cartService;

    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Order> orders = new ArrayList<>();
        when(orderService.findAll()).thenReturn(orders);

        ResponseEntity<List<Order>> response = orderController.findAll();

        assertEquals(OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
        verify(orderService, times(1)).findAll();
    }

    @Test
    void testGetOrdersByUser() {
        String userEmail = "user@example.com";
        List<OrderDto> orders = new ArrayList<>();
        when(principal.getName()).thenReturn(userEmail);
        when(orderService.getAllByUserEmail(userEmail)).thenReturn(orders);

        ResponseEntity<List<OrderDto>> response = orderController.getOrdersByUser(principal);

        assertEquals(OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
        verify(orderService, times(1)).getAllByUserEmail(userEmail);
    }

    @Test
    void testGetOrdersByUserId() {
        Integer userId = 1;
        List<OrderDto> orders = new ArrayList<>();
        when(orderService.getAllByUserId(userId)).thenReturn(orders);

        ResponseEntity<List<OrderDto>> response = orderController.getOrdersByUserId(userId);

        assertEquals(OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
        verify(orderService, times(1)).getAllByUserId(userId);
    }

    @Test
    void testFindById() {
        int orderId = 1;
        Order order = new Order();
        when(orderService.findById(orderId)).thenReturn(order);

        ResponseEntity<Order> response = orderController.findById(orderId);

        assertEquals(OK, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService, times(1)).findById(orderId);
    }

    @Test
    void testCreateOrder() throws IOException {
        MultiValueMap<String, String> formData = mock(MultiValueMap.class);
        UserDto user = new UserDto();
        user.setEmail("user@example.com");
        CartDto cartDto = new CartDto();
        cartDto.setCartDetails(new ArrayList<>());

        when(userService.getUser(1)).thenReturn(user);
        when(cartService.getCart(user.getEmail())).thenReturn(cartDto);
        OrderDto orderDto = new OrderDto();
        OrderDto savedOrderDto = new OrderDto();
        when(orderService.createOrder(any(OrderDto.class))).thenReturn(savedOrderDto);
        when(orderDetailsService.createOrderDetail(any(CartDetailDto.class), anyInt())).thenReturn(new OrderDetailDto());

        ResponseEntity<OrderDto> response = orderController.CreateOrder(formData);

        assertEquals(OK, response.getStatusCode());
        assertEquals(savedOrderDto, response.getBody());
        verify(orderService, times(1)).createOrder(any(OrderDto.class));
    }

    @Test
    void testUpdateOrderStatus() throws IOException {
        MultiValueMap<String, String> formData = mock(MultiValueMap.class);
        int orderId = 1;
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderStatus(1);
        OrderDto savedOrderDto = new OrderDto();
        when(orderService.updateOrder(any(OrderDto.class), eq(orderId))).thenReturn(savedOrderDto);
        when(formData.getFirst("orderStatus")).thenReturn("1");

        ResponseEntity<OrderDto> response = orderController.UpdateOrderStatus(formData, orderId);

        assertEquals(OK, response.getStatusCode());
        assertEquals(savedOrderDto, response.getBody());
        verify(orderService, times(1)).updateOrder(any(OrderDto.class), eq(orderId));
    }

    @Test
    void testDeleteOrder() {
        int orderId = 1;
        doNothing().when(orderService).deleteOrder(orderId);

        ResponseEntity<ApiResponse> response = orderController.Delete(orderId);

        assertEquals(OK, response.getStatusCode());
        assertEquals("Order deleted", response.getBody().getMessage());
        verify(orderService, times(1)).deleteOrder(orderId);
    }
}