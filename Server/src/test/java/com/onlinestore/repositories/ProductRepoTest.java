package com.onlinestore.repositories;

import com.onlinestore.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class ProductRepoTest {

    @Autowired
    private ProductRepo productRepo;

    @Test
    @Transactional
    public void testSave_ShouldPersistProductEntity() {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("This is a test product description");
        product.setPrice(10.0);
        product.setWeight(1.0);

        Product savedProduct = productRepo.save(product);

        assertNotNull(savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getDescription(), savedProduct.getDescription());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        assertEquals(product.getWeight(), savedProduct.getWeight());
    }

    @Test
    @Transactional
    public void testFindById_ShouldReturnProductForExistingId() {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("This is a test product description");
        product.setPrice(10.0);
        product.setWeight(1.0);
        Product savedProduct = productRepo.save(product);

        int productId = savedProduct.getId();

        Product foundProduct = productRepo.findById(productId).get();

        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getId());
        assertEquals(product.getName(), foundProduct.getName());
        assertEquals(product.getDescription(), foundProduct.getDescription());
        assertEquals(product.getPrice(), foundProduct.getPrice());
        assertEquals(product.getWeight(), foundProduct.getWeight());
    }

    @Test
    @Transactional
    public void testSave_ShouldUpdateExistingProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("This is a test product description");
        product.setPrice(10.0);
        product.setWeight(1.0);
        Product savedProduct = productRepo.save(product);

        savedProduct.setName("Updated Product");
        savedProduct.setDescription("Updated description");
        savedProduct.setPrice(20.0);
        savedProduct.setWeight(2.0);

        Product updatedProduct = productRepo.save(savedProduct);

        assertEquals(savedProduct.getId(), updatedProduct.getId());
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals("Updated description", updatedProduct.getDescription());
        assertEquals(20.0, updatedProduct.getPrice());
        assertEquals(2.0, updatedProduct.getWeight());
    }

    @Test
    @Transactional
    public void testDeleteById_ShouldRemoveProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("This is a test product description");
        product.setPrice(10.0);
        product.setWeight(1.0);
        Product savedProduct = productRepo.save(product);

        int productId = savedProduct.getId();

        productRepo.deleteById(productId);

        Optional<Product> deletedProduct = productRepo.findById(productId);

        assertFalse(deletedProduct.isPresent());
    }
}