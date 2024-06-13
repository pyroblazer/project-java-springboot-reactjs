package com.onlinestore.repositories;

import com.onlinestore.entities.Cart;
import com.onlinestore.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class CartRepoTest {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testSave_ShouldPersistCartEntity() {
        Cart cart = new Cart();
        cart.setTotalAmount(0.0);

        Cart savedCart = cartRepo.save(cart);

        assertNotNull(savedCart.getId());
        assertEquals(cart.getTotalAmount(), savedCart.getTotalAmount());
    }

    @Test
    @Transactional
    public void testFindByUser_ShouldReturnCartForUser() {
        // Create a user
        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        entityManager.persist(user);

        // Create a cart associated with the user
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalAmount(0.0);
        entityManager.persist(cart);

        // Flush changes to ensure data is persisted
        entityManager.flush();

        // Find cart by user
        Cart foundCart = cartRepo.findByUser(user);

        // Assert that the found cart matches the persisted one
        assertNotNull(foundCart);
        assertEquals(cart.getId(), foundCart.getId());
        assertEquals(cart.getUser(), foundCart.getUser());
        assertEquals(cart.getTotalAmount(), foundCart.getTotalAmount());
    }

    @Test
    @Transactional
    public void testFindById_ShouldReturnCartForExistingId() {
        Cart cart = new Cart();
        cart.setTotalAmount(0.0);
        Cart savedCart = cartRepo.save(cart);

        int cartId = savedCart.getId();

        Cart foundCart = cartRepo.findById(cartId).get();

        assertNotNull(foundCart);
        assertEquals(cartId, foundCart.getId());
        assertEquals(cart.getTotalAmount(), foundCart.getTotalAmount());
    }

    @Test
    @Transactional
    public void testSave_ShouldUpdateExistingCart() {
        Cart cart = new Cart();
        cart.setTotalAmount(0.0);
        Cart savedCart = cartRepo.save(cart);

        savedCart.setTotalAmount(10.0);
        Cart updatedCart = cartRepo.save(savedCart);

        assertEquals(savedCart.getId(), updatedCart.getId());
        assertEquals(10.0, updatedCart.getTotalAmount());
    }

    @Test
    @Transactional
    public void testDeleteById_ShouldRemoveCart() {
        Cart cart = new Cart();
        cart.setTotalAmount(0.0);
        Cart savedCart = cartRepo.save(cart);

        int cartId = savedCart.getId();

        cartRepo.deleteById(cartId);

        Optional<Cart> deletedCart = cartRepo.findById(cartId);

        assertFalse(deletedCart.isPresent());
    }
}