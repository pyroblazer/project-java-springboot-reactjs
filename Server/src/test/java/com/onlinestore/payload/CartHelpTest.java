package com.onlinestore.payload;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class CartHelpTest {

    @Test
    public void testCartHelp_SettersAndGetters() {
        String userEmail = "user@example.com";
        int productId = 101;
        int quantity = 2;

        CartHelp cartHelp = new CartHelp(userEmail, productId, quantity);

        assertEquals(userEmail, cartHelp.getUserEmail());
        assertEquals(productId, cartHelp.getProductId());
        assertEquals(quantity, cartHelp.getQuantity());

        String newEmail = "updated@example.com";
        int newQuantity = 5;

        cartHelp.setUserEmail(newEmail);
        cartHelp.setQuantity(newQuantity);

        assertEquals(newEmail, cartHelp.getUserEmail());
        assertEquals(newQuantity, cartHelp.getQuantity());
    }

    @Test
    public void testCartHelp_NoArgsConstructor() {
        CartHelp cartHelp = new CartHelp();

        assertNull(cartHelp.getUserEmail());
        assertEquals(0, cartHelp.getProductId());
        assertEquals(0, cartHelp.getQuantity());
    }

//    @Test
//    public void testCartHelp_InvalidQuantity() {
//        String userEmail = "user@example.com";
//        int productId = 101;
//
//        assertThrows(IllegalArgumentException.class, () -> new CartHelp(userEmail, productId, -2));
//    }
}