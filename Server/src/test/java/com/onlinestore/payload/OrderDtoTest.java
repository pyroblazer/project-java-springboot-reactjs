package com.onlinestore.payload;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
public class OrderDtoTest {

    @Test
    public void testOrderDto_NoArgsConstructor_ShouldCreateEmptyObject() {
        OrderDto orderDto = new OrderDto();

        assertAll("orderDto",
                () -> assertEquals(0, orderDto.getId()),
                () -> assertNull(orderDto.getUser()),
                () -> assertNull(orderDto.getMoment()),
                () -> assertNull(orderDto.getOrderDetails()),
                () -> assertNull(orderDto.getOrderStatus()),
                () -> assertEquals(0.0, orderDto.getTotalAmount()));
    }

    @Test
    public void testOrderDto_AllArgsConstructor_ShouldSetValuesCorrectly() {
        UserDto userDto = new UserDto(1, "John Doe", "johndoe@example.com", "password123", "USER");
        Instant moment = Instant.now();
        List<OrderDetailDto> orderDetails = new ArrayList<>();
        Integer orderStatus = 1; // Assuming orderStatus represents different states (e.g., 1: Placed)
        double totalAmount = 19.99;

        OrderDto orderDto = new OrderDto(1, userDto, moment, orderDetails, orderStatus, totalAmount);

        assertAll("orderDto",
                () -> assertEquals(1, orderDto.getId()),
                () -> assertEquals(userDto, orderDto.getUser()),
                () -> assertEquals(moment, orderDto.getMoment()),
                () -> assertEquals(orderDetails, orderDto.getOrderDetails()),
                () -> assertEquals(orderStatus, orderDto.getOrderStatus()),
                () -> assertEquals(totalAmount, orderDto.getTotalAmount()));
    }

    @Test
    public void testOrderDto_SettersAndGetters_ShouldWorkAsExpected() {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(2);
        UserDto userDto = new UserDto(); // UserDto can be further populated
        Instant moment = Instant.now();
        List<OrderDetailDto> orderDetails = new ArrayList<>();
        orderDto.setUser(userDto);
        orderDto.setMoment(moment);
        orderDto.setOrderDetails(orderDetails);
        orderDto.setOrderStatus(2); // Assuming orderStatus represents different states
        orderDto.setTotalAmount(39.98);

        assertAll("orderDto",
                () -> assertEquals(2, orderDto.getId()),
                () -> assertEquals(userDto, orderDto.getUser()),
                () -> assertEquals(moment, orderDto.getMoment()),
                () -> assertEquals(orderDetails, orderDto.getOrderDetails()),
                () -> assertEquals(2, orderDto.getOrderStatus()),
                () -> assertEquals(39.98, orderDto.getTotalAmount()));
    }
}