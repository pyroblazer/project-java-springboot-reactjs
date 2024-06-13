package com.onlinestore.services.impl;

import com.onlinestore.entities.Cart;
import com.onlinestore.entities.CartDetails;
import com.onlinestore.repositories.CartDetailsRepo;
import com.onlinestore.repositories.CartRepo;
import com.onlinestore.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class CartDetailsServiceImplTest {

    @InjectMocks
    private CartDetailsServiceImpl cartDetailsService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private CartRepo cartRepo;

    @Mock
    private CartDetailsRepo cartDetailsRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeleteAllCartDetailsByCartId() {
        Cart cart = Cart.builder().id(1).build();
        CartDetails cartDetails1 = CartDetails.builder().id(1).build();
        CartDetails cartDetails2 = CartDetails.builder().id(2).build();
        List<CartDetails> cartDetailsList = new ArrayList<>();
        cartDetailsList.add(cartDetails1);
        cartDetailsList.add(cartDetails2);
        cart.setCartDetails(cartDetailsList);

        when(cartRepo.findById(anyInt())).thenReturn(Optional.of(cart));

        cartDetailsService.deleteAllCartDetailsByCartId(1);

        verify(cartRepo, times(1)).findById(anyInt());
        verify(cartDetailsRepo, times(1)).deleteById(1);
        verify(cartDetailsRepo, times(1)).deleteById(2);
    }
}