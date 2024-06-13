package com.onlinestore.entities;

import com.onlinestore.repositories.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ProductTest {

    @Autowired
    private ProductRepo productRepo;

    @BeforeEach
    public void clearDatabase() {
        productRepo.deleteAll(); // Clear any existing data before each test
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("This is a test product for testing purposes.");
        product.setPrice(19.99d);
        product.setWeight(2.0d);

        Product savedProduct = productRepo.save(product);

        assertNotNull(savedProduct);
        assertTrue(savedProduct.getId() > 0);
    }

    @Test
    public void testFindProductById() {
        Product product = new Product();
        product.setName("Test Product 2");
        product.setDescription("This is another test product.");
        product.setPrice(29.99d);
        product.setWeight(1.5d);

        product = productRepo.save(product); // Create a new product for this test

        Product foundProduct = productRepo.findById(product.getId()).get();

        assertNotNull(foundProduct);
        assertEquals("Test Product 2", foundProduct.getName());
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product();
        product.setName("Test Product 3");
        product.setDescription("This is a third test product.");
        product.setPrice(34.99d);
        product.setWeight(3.0d);

        product = productRepo.save(product); // Create a new product for this test

        product.setPrice(39.99d);

        productRepo.save(product);

        product = productRepo.findById(product.getId()).get();

        assertEquals(39.99d, product.getPrice(), 0.01d); // Allow for slight doubleing-point precision differences
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product();
        product.setName("Test Product 4");
        product.setDescription("This is a fourth test product.");
        product.setPrice(49.99d);
        product.setWeight(0.5d);

        product = productRepo.save(product); // Create a new product for this test

        productRepo.delete(product);

        Optional<Product> deletedProduct = productRepo.findById(product.getId());

        assertFalse(deletedProduct.isPresent());
    }
}