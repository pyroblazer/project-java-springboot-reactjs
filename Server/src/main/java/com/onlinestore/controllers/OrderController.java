package com.onlinestore.controllers;

import com.onlinestore.entities.Order;
import com.onlinestore.payload.*;
import com.onlinestore.payload.OrderDto;
import com.onlinestore.services.CartService;
import com.onlinestore.services.OrderDetailsService;
import com.onlinestore.services.OrderService;
import com.onlinestore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    OrderDetailsService orderDetailsService;

    @Autowired
    CartService cartService;

    @GetMapping
    public ResponseEntity<List<Order>> findAll(){
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok().body(orders);
    }

    // Read
    @GetMapping(value = "/user")
    public ResponseEntity<List<OrderDto>> getOrdersByUser(Principal principal) {
        String userEmail = principal.getName();
        List<OrderDto> orders = orderService.getAllByUserEmail(userEmail);
        return ResponseEntity.ok().body(orders);
    }

    // Read
    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@RequestParam Integer userId) {
        List<OrderDto> orders = orderService.getAllByUserId(userId);
        return ResponseEntity.ok().body(orders);
    }

    // Read
    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> findById(@PathVariable int id){
        Order order = orderService.findById(id);
        return ResponseEntity.ok().body(order);
    }

    // Create
    @PostMapping(value = "/add" )
    public ResponseEntity<OrderDto> CreateOrder(@RequestParam MultiValueMap<String, String> formData) throws IOException {
        OrderDto orderDto =  new OrderDto();
        System.out.println(formData);
        UserDto user = userService.getUser(1);
        Instant moment = Instant.now();

        CartDto cartDto = this.cartService.getCart(user.getEmail());
        List<CartDetailDto> cartDetailDtos = cartDto.getCartDetails();


        List<OrderDetailDto> orderDetailDtos = new ArrayList<>();

        orderDto.setUser(user);
        orderDto.setMoment(moment);
        orderDto.setOrderStatus(1);
        orderDto.setOrderDetails(orderDetailDtos);

        OrderDto savedOrderDto = this.orderService.createOrder(orderDto);

        for (CartDetailDto cartDetailDto : cartDetailDtos) {
            orderDetailDtos.add(orderDetailsService.createOrderDetail(cartDetailDto, savedOrderDto.getId()));
        }

        return new ResponseEntity<OrderDto>(savedOrderDto, HttpStatusCode.valueOf(200));
    }

    // Update
    @PutMapping(value = "/updateOrderStatus/{orderId}" )
    public ResponseEntity<OrderDto> UpdateOrderStatus(@RequestParam MultiValueMap<String, String> formData, @PathVariable int orderId) throws IOException {
        OrderDto orderDto = new OrderDto();
        Integer orderStatus = parseInt(formData.getFirst("orderStatus"));

        orderDto.setOrderStatus(orderStatus);

        OrderDto savedOrderDto = this.orderService.updateOrder(orderDto, orderId);

        return new ResponseEntity<OrderDto>(savedOrderDto, HttpStatusCode.valueOf(200));
    }

    // Delete
    @DeleteMapping(value = "/del/{OrderId}",produces = "application/json")
    public ResponseEntity<ApiResponse> Delete(@PathVariable int OrderId){
        this.orderService.deleteOrder(OrderId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Order deleted"),HttpStatusCode.valueOf(200));
    }
}