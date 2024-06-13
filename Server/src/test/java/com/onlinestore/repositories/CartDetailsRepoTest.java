package com.onlinestore.repositories;

import com.onlinestore.entities.Cart;
import com.onlinestore.entities.CartDetails;
import com.onlinestore.entities.Product;
import com.onlinestore.entities.User;
import com.onlinestore.utils.TestDataUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.onlinestore.utils.TestDataUtil.productId;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class CartDetailsRepoTest {

    @Autowired
    private CartDetailsRepo cartDetailsRepo;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testSave_ShouldPersistCartDetailsEntity() {
        Product product = TestDataUtil.createProduct();
        Cart cart = new Cart();  // Assuming a new Cart object
        CartDetails cartDetails = new CartDetails();
        cartDetails.setProduct(product);
        cartDetails.setCart(cart);
        cartDetails.setQuantity(2);
        cartDetails.setAmount(product.getPrice() * 2);

        CartDetails savedDetails = cartDetailsRepo.save(cartDetails);

        assertNotNull(savedDetails.getId());
        assertEquals(cartDetails.getProduct(), savedDetails.getProduct());
        assertEquals(cartDetails.getCart(), savedDetails.getCart());
        assertEquals(cartDetails.getQuantity(), savedDetails.getQuantity());
        assertEquals(cartDetails.getAmount(), savedDetails.getAmount());
    }

    @Test
    @Transactional
    public void testFindById_ShouldReturnCartDetailsForExistingId() {
        Product product = TestDataUtil.createProduct();
        Cart cart = new Cart();
        CartDetails cartDetails = new CartDetails();
        cartDetails.setProduct(product);
        cartDetails.setCart(cart);
        cartDetails.setQuantity(2);
        cartDetails.setAmount(product.getPrice() * 2);

        cartDetailsRepo.save(cartDetails);

        CartDetails foundDetails = cartDetailsRepo.findById(cartDetails.getId()).get();

        assertNotNull(foundDetails);
        assertEquals(cartDetails.getId(), foundDetails.getId());
        assertEquals(cartDetails.getProduct(), foundDetails.getProduct());
        assertEquals(cartDetails.getCart(), foundDetails.getCart());
        assertEquals(cartDetails.getQuantity(), foundDetails.getQuantity());
        assertEquals(cartDetails.getAmount(), foundDetails.getAmount());
    }

    @Test
    @Transactional
    public void testSave_ShouldUpdateExistingCartDetails() {
        Product product = TestDataUtil.createProduct();
        Cart cart = new Cart();
        CartDetails cartDetails = new CartDetails();
        cartDetails.setProduct(product);
        cartDetails.setCart(cart);
        cartDetails.setQuantity(2);
        cartDetails.setAmount(product.getPrice() * 2);

        cartDetailsRepo.save(cartDetails);

        CartDetails retrievedDetails = cartDetailsRepo.findById(cartDetails.getId()).get();
        retrievedDetails.setQuantity(3);
        retrievedDetails.setAmount(retrievedDetails.getProduct().getPrice() * 3);

        CartDetails updatedDetails = cartDetailsRepo.save(retrievedDetails);

        assertEquals(retrievedDetails.getQuantity(), updatedDetails.getQuantity());
        assertEquals(retrievedDetails.getAmount(), updatedDetails.getAmount());
    }

    @Test
    @Transactional
    public void testDeleteById_ShouldRemoveCartDetails() {
        Product product = TestDataUtil.createProduct();
        Cart cart = new Cart();
        CartDetails cartDetails = new CartDetails();
        cartDetails.setProduct(product);
        cartDetails.setCart(cart);
        cartDetails.setQuantity(2);
        cartDetails.setAmount(product.getPrice() * 2);

        cartDetailsRepo.save(cartDetails);

        int cartDetailsId = cartDetails.getId();

        cartDetailsRepo.deleteById(cartDetailsId);

        Optional<CartDetails> deletedDetails = cartDetailsRepo.findById(cartDetailsId);

        assertFalse(deletedDetails.isPresent());
    }

    @Test
    @Transactional
    public void testFindByProductIdAndCartId_ShouldReturnMatchingCartDetails() {
        Product product = TestDataUtil.createProduct();
        User user = new User();
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCartDetails(new ArrayList<>());

        entityManager.persist(cart);
        entityManager.persist(user);
        entityManager.persist(product);


        CartDetails cartDetails = new CartDetails();
        cartDetails.setProduct(product);
        cartDetails.setCart(cart);
        cartDetails.setQuantity(2);
        cartDetails.setAmount(product.getPrice() * 2);
        cart.setTotalAmount(cart.getTotalAmount() + cartDetails.getAmount());
        List<CartDetails> cartDetailsList = new ArrayList<>();
        cartDetailsList.add(cartDetails);
        cart.setCartDetails(cartDetailsList);

        cartDetailsRepo.save(cartDetails);

        CartDetails foundCartDetails = cartDetailsRepo.findByProductIdAndCartId(productId, 1);

        assertNotNull(foundCartDetails);
        assertEquals(cartDetails.getId(), foundCartDetails.getId());
        assertEquals(cartDetails.getProduct(), foundCartDetails.getProduct());
        assertEquals(cartDetails.getCart(), foundCartDetails.getCart());
        assertEquals(cartDetails.getQuantity(), foundCartDetails.getQuantity());
        assertEquals(cartDetails.getAmount(), foundCartDetails.getAmount());
    }
}