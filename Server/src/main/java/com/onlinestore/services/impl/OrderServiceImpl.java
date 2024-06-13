package com.onlinestore.services.impl;

import com.onlinestore.entities.*;
import com.onlinestore.payload.*;
import com.onlinestore.repositories.OrderRepo;
import com.onlinestore.repositories.ProductRepo;
import com.onlinestore.repositories.UserRepo;
import com.onlinestore.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.onlinestore.utils.MiscUtils.decompressBytes;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {

        Order newOrder = new Order();

        UserDto userDto = orderDto.getUser();
        int userId = userDto.getId();
        newOrder.setUser(userRepo.findById(userId).orElseThrow());
        newOrder.setMoment(Instant.now());
        newOrder.setTotalAmount(orderDto.getTotalAmount());

        Order savedOrder = this.orderRepo.save(newOrder);

        return this.modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    @Override
    public List<OrderDto> getAllByUserId(int userId) {
        List<Order> orderList = orderRepo.findOrdersByUserId(userId);
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (Order order : orderList) {
            OrderDto orderDto = modelMapper.map(order, OrderDto.class);
            orderDtoList.add(orderDto);
        }

        return orderDtoList;
    }

    @Override
    public List<OrderDto> getAllByUserEmail(String userEmail) {
        List<Order> orderList = orderRepo.findOrdersByUserEmail(userEmail);
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (Order order : orderList) {
            OrderDto orderDto = modelMapper.map(order, OrderDto.class);
            orderDtoList.add(orderDto);
        }

        return orderDtoList;
    }

    @Override
    public Order findById(int id) {
        System.out.println("Find By ID");
        System.out.println(id);
        return orderRepo.findById(id).orElseThrow();
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto, int orderId) {
        Order newOrder = this.orderRepo.findById(orderDto.getId()).orElseThrow();
        // newOrder.setOrderDetails(orderDto.getOrderDetails());
        newOrder.setOrderStatus(orderDto.getOrderStatus());
        newOrder.setMoment(orderDto.getMoment());
        // newOrder.setUser(orderDto.getUser());
        Order savedNewOrder = this.orderRepo.save(newOrder);
        return this.modelMapper.map(savedNewOrder, OrderDto.class);
    };

    @Override
    public void deleteOrder(int orderId) {
        this.orderRepo.deleteById(orderId);
        return;
    };


    @Override
    public OrderDto addProductToOrder(OrderHelp orderHelp, int orderId) {
        int productId = orderHelp.getProductId();
        int quantity = orderHelp.getQuantity();

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        OrderDetails orderDetails = order.getOrderDetails().stream()
                .filter(od -> od.getProduct().getId() == productId)
                .findFirst()
                .orElseGet(() -> {
                    OrderDetails newOrderDetails = new OrderDetails();
                    newOrderDetails.setProduct(product);
                    newOrderDetails.setOrder(order);
                    newOrderDetails.setQuantity(0);
                    order.getOrderDetails().add(newOrderDetails);
                    return newOrderDetails;
                });

        orderDetails.setQuantity(orderDetails.getQuantity() + quantity);
        orderDetails.setAmount(orderDetails.getQuantity() * product.getPrice());

        order.setTotalAmount(order.getOrderDetails().stream()
                .mapToDouble(OrderDetails::getAmount)
                .sum());

        Order savedOrder = orderRepo.save(order);

        return mapOrderToDto(savedOrder);
    }

    private OrderDto mapOrderToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setMoment(order.getMoment());
        orderDto.setOrderStatus(order.getOrderStatus());
        orderDto.setTotalAmount(order.getTotalAmount());

        // Map User to UserDto
        if (order.getUser() != null) {
            UserDto userDto = new UserDto();
            userDto.setId(order.getUser().getId());
            userDto.setName(order.getUser().getName());
            userDto.setEmail(order.getUser().getEmail());
            // Set other user fields as needed
            orderDto.setUser(userDto);
        }

        // Map OrderDetails to OrderDetailDto
        List<OrderDetailDto> orderDetailDtos = new ArrayList<>();
        for (OrderDetails orderDetails : order.getOrderDetails()) {
            OrderDetailDto orderDetailDto = new OrderDetailDto();
            orderDetailDto.setId(orderDetails.getId());
            orderDetailDto.setQuantity(orderDetails.getQuantity());
            orderDetailDto.setAmount(orderDetails.getAmount());

            // Map Product to ProductDto
            if (orderDetails.getProduct() != null) {
                ProductDto productDto = new ProductDto();
                productDto.setId(orderDetails.getProduct().getId());
                productDto.setName(orderDetails.getProduct().getName());
                productDto.setDescription(orderDetails.getProduct().getDescription());
                productDto.setPrice(orderDetails.getProduct().getPrice());
                productDto.setWeight(orderDetails.getProduct().getWeight());
                productDto.setImg(decompressBytes(orderDetails.getProduct().getImg()));
                orderDetailDto.setProduct(productDto);
            }

            orderDetailDtos.add(orderDetailDto);
        }
        orderDto.setOrderDetails(orderDetailDtos);

        return orderDto;
    }
}
