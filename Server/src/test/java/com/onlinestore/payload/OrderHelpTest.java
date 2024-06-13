package com.onlinestore.payload;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class OrderHelpTest {

    @Test
    public void testOrderHelp_SettersAndGetters() {
        UserDto userDto = new UserDto(1, "John Doe", "johndoe@example.com", "password123", "USER");
        int productId = 101;
        int quantity = 2;

        OrderHelp orderHelp = new OrderHelp(userDto.getId(), productId, quantity);

        assertEquals(userDto.getId(), orderHelp.getUserId());
        assertEquals(productId, orderHelp.getProductId());
        assertEquals(quantity, orderHelp.getQuantity());

        int newUserId = 2;
        int newQuantity = 5;

        orderHelp.setUserId(newUserId);
        orderHelp.setQuantity(newQuantity);

        assertEquals(newUserId, orderHelp.getUserId());
        assertEquals(newQuantity, orderHelp.getQuantity());
    }

    @Test
    public void testOrderHelp_NoArgsConstructor() {
        OrderHelp orderHelp = new OrderHelp();

        assertEquals(0, orderHelp.getUserId());
        assertEquals(0, orderHelp.getProductId());
        assertEquals(0, orderHelp.getQuantity());
    }
}