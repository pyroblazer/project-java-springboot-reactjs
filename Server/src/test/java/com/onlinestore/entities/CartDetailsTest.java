package com.onlinestore.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CartDetailsTest {

    @Test
    public void testCartDetailsCreation() {
        Product product = new Product();
        product.setName("Test Product");

        Cart cart = new Cart();

        CartDetails cartDetails = new CartDetails();
        cartDetails.setProduct(product);
        cartDetails.setCart(cart);
        cartDetails.setQuantity(1);

        assertNotNull(cartDetails);
        assertEquals(0, cartDetails.getId());
        assertEquals(product, cartDetails.getProduct());
        assertEquals(cart, cartDetails.getCart());
    }
}
