package com.onlinestore.payload;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class ProductDtoTest {

    @Test
    public void testProductDto_SettersAndGetters() {
        ProductDto product = new ProductDto(
                1, "Shirt", "Cotton shirt", 15.99d, 0.3d, null);

        assertEquals(1, product.getId());
        assertEquals("Shirt", product.getName());

        product.setDescription("Updated Cotton Shirt");
        assertEquals("Updated Cotton Shirt", product.getDescription());
    }
}