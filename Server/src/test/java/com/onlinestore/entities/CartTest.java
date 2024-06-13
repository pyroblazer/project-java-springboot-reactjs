package com.onlinestore.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CartTest {

    @Test
    public void testCartCreation() {
        User user = new User();
        user.setEmail("test@example.com");

        Cart cart = new Cart();
        cart.setUser(user);

        assertNotNull(cart);
        assertEquals(0, cart.getId());
        assertEquals(Collections.emptyList(), cart.getCartDetails());
    }

    @Test
    public void testCartDetailsAddition() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(10d);

        CartDetails cartDetails = new CartDetails();
        cartDetails.setProduct(product);
        cartDetails.setQuantity(2);

        Cart cart = new Cart();
        cart.setCartDetails(new ArrayList<>());

        cart.getCartDetails().add(cartDetails);

        assertEquals(1, cart.getCartDetails().size());
        assertEquals(cartDetails, cart.getCartDetails().get(0));

    }
}
