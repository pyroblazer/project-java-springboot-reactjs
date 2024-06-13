package com.onlinestore.repositories;

import com.onlinestore.entities.OrderDetails;
import com.onlinestore.entities.Product;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.onlinestore.utils.TestDataUtil.createOrderDetails;
import static com.onlinestore.utils.TestDataUtil.createProduct;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class OrderDetailsRepoTest {

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testSave_ShouldPersistOrderDetailsEntity() {
        Product product = createProduct();
        OrderDetails orderDetails = createOrderDetails(product);

        OrderDetails savedDetails = orderDetailsRepo.save(orderDetails);

        assertNotNull(savedDetails.getId());
        assertEquals(orderDetails.getProduct(), savedDetails.getProduct());
        assertEquals(orderDetails.getQuantity(), savedDetails.getQuantity());
        assertEquals(orderDetails.getAmount(), savedDetails.getAmount());
    }

    @Test
    @Transactional
    public void testFindById_ShouldReturnOrderDetailsForExistingId() {
        Product product = createProduct();
        OrderDetails orderDetails = createOrderDetails(product);
        orderDetailsRepo.save(orderDetails);

        OrderDetails foundDetails = orderDetailsRepo.findById(orderDetails.getId()).get();

        assertNotNull(foundDetails);
        assertEquals(orderDetails.getId(), foundDetails.getId());
        assertEquals(orderDetails.getProduct(), foundDetails.getProduct());
        assertEquals(orderDetails.getQuantity(), foundDetails.getQuantity());
        assertEquals(orderDetails.getAmount(), foundDetails.getAmount());
    }

    @Test
    @Transactional
    public void testSave_ShouldUpdateExistingOrderDetails() {
        Product product = createProduct();
        OrderDetails orderDetails = createOrderDetails(product);
        orderDetailsRepo.save(orderDetails);

        OrderDetails retrievedDetails = orderDetailsRepo.findById(orderDetails.getId()).get();
        retrievedDetails.setQuantity(3);
        retrievedDetails.setAmount(retrievedDetails.getProduct().getPrice() * 3);

        OrderDetails updatedDetails = orderDetailsRepo.save(retrievedDetails);

        assertEquals(retrievedDetails.getQuantity(), updatedDetails.getQuantity());
        assertEquals(retrievedDetails.getAmount(), updatedDetails.getAmount());
    }

    @Test
    @Transactional
    public void testDeleteById_ShouldRemoveOrderDetails() {
        Product product = createProduct();
        OrderDetails orderDetails = createOrderDetails(product);
        orderDetailsRepo.save(orderDetails);

        int orderDetailsId = orderDetails.getId();

        orderDetailsRepo.deleteById(orderDetailsId);

        Optional<OrderDetails> deletedDetails = orderDetailsRepo.findById(orderDetailsId);

        assertFalse(deletedDetails.isPresent());
    }
}