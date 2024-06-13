package com.onlinestore.repositories;

import com.onlinestore.entities.Order;
import com.onlinestore.entities.OrderDetails;
import com.onlinestore.entities.Product;
import com.onlinestore.entities.User;
import com.onlinestore.utils.TestDataUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.onlinestore.utils.TestDataUtil.productPrice;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class OrderRepoTest {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private EntityManager entityManager;

    private User createUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        return user;
    }

    @Test
    @Transactional
    public void testSave_ShouldPersistOrderEntity() {
        User user = createUser();
        entityManager.persist(user);

        Instant now = Instant.now();
        Order order = new Order();
        order.setUser(user);
        order.setMoment(now);
        order.setOrderStatus(1);
        order.setTotalAmount(0.0);

        Order savedOrder = orderRepo.save(order);

        assertNotNull(savedOrder.getId());
        assertEquals(order.getUser(), savedOrder.getUser());
        assertEquals(now, savedOrder.getMoment());
        assertEquals(1, savedOrder.getOrderStatus());
        assertEquals(0.0, savedOrder.getTotalAmount());
    }

    @Test
    @Transactional
    public void testFindById_ShouldReturnOrderForExistingId() {
        User user = createUser();
        entityManager.persist(user);  // Persist user before creating order

        Instant now = Instant.now();
        Order order = new Order();
        order.setUser(user);
        order.setMoment(now);
        order.setOrderStatus(2);  // Modify orderStatus
        order.setTotalAmount(100.0);
        order = orderRepo.save(order);

        Order foundOrder = orderRepo.findById(order.getId()).get();

        assertNotNull(foundOrder);
        assertEquals(order.getId(), foundOrder.getId());
        assertEquals(order.getUser(), foundOrder.getUser());
        assertEquals(now, foundOrder.getMoment());
        assertEquals(2, foundOrder.getOrderStatus());
        assertEquals(100.0, foundOrder.getTotalAmount());
    }

    @Test
    @Transactional
    public void testDeleteById_ShouldRemoveOrder() {
        User user = createUser();
        entityManager.persist(user);

        Order order = new Order();
        order.setUser(user);

        order = orderRepo.save(order);

        int orderId = order.getId();

        orderRepo.deleteById(orderId);

        Optional<Order> deletedOrder = orderRepo.findById(orderId);

        assertFalse(deletedOrder.isPresent());
    }

    @Test
    @Transactional
    public void testFindOrdersByUserId_ShouldReturnOrdersForUser() {
        User user = createUser();
        entityManager.persist(user);

        Instant now = Instant.now();

        // Order 1 with OrderDetails
        Product product1 = TestDataUtil.createProduct();
        entityManager.persist(product1);

        Order order1 = new Order();
        order1.setUser(user);
        order1.setMoment(now);
        order1.setOrderStatus(1);
        order1.setTotalAmount(50.0);
        OrderDetails orderDetails1 = TestDataUtil.createOrderDetails(product1);
        order1.getOrderDetails().add(orderDetails1);

        orderRepo.save(order1);

        Product product2 = TestDataUtil.createProduct();
        product2.setPrice(10.0);
        product2.setName("Another Product");
        entityManager.persist(product2);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setMoment(now.plusSeconds(10));
        order2.setOrderStatus(2);
        order2.setTotalAmount(100.0);
        OrderDetails orderDetails2 = TestDataUtil.createOrderDetails(product2);
        order2.getOrderDetails().add(orderDetails2);

        orderRepo.save(order2);

        List<Order> foundOrders = orderRepo.findOrdersByUserId(user.getId());

        assertEquals(2, foundOrders.size()); // Assert two orders found
        for (Order foundOrder : foundOrders) {
            assertEquals(user, foundOrder.getUser());
            // Assert properties based on whether it's order1 or order2
            if (foundOrder.getMoment().equals(now)) {
                assertEquals(1, foundOrder.getOrderStatus());
                assertEquals(50.0, foundOrder.getTotalAmount());
                assertEquals(1, foundOrder.getOrderDetails().size());  // Assert one OrderDetail
                OrderDetails foundOrderDetail1 = foundOrder.getOrderDetails().get(0);
                assertEquals(product1, foundOrderDetail1.getProduct());  // Assert product in OrderDetail
                assertEquals(2, foundOrderDetail1.getQuantity());  // Assert quantity
                assertEquals(productPrice * 2, foundOrderDetail1.getAmount());  // Assert amount (price * quantity)
            } else {
                assertEquals(now.plusSeconds(10), foundOrder.getMoment());
                assertEquals(2, foundOrder.getOrderStatus());
                assertEquals(100.0, foundOrder.getTotalAmount());
                assertEquals(1, foundOrder.getOrderDetails().size());  // Assert one OrderDetail
                OrderDetails foundOrderDetail2 = foundOrder.getOrderDetails().get(0);
                assertEquals(product2, foundOrderDetail2.getProduct());  // Assert product in OrderDetail (modified product)
                assertEquals(2, foundOrderDetail2.getQuantity());  // Assert quantity
                assertEquals(10.0 * 2, foundOrderDetail2.getAmount());  // Assert amount (price * quantity)
            }
        }
    }

    @Test
    @Transactional
    public void testFindOrdersByUserEmail_ShouldReturnOrdersForUserEmail() {
        User user = createUser();
        entityManager.persist(user);

        Order order = new Order();
        order.setUser(user);
        // Set other order properties
        orderRepo.save(order);

        List<Order> foundOrders = orderRepo.findOrdersByUserEmail(user.getEmail());

        assertEquals(1, foundOrders.size()); // Assert one order found
        assertEquals(user, foundOrders.get(0).getUser());
        // Assert other order properties
    }
}
