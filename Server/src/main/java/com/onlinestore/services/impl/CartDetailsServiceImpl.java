package com.onlinestore.services.impl;

import com.onlinestore.entities.Cart;
import com.onlinestore.entities.CartDetails;
import com.onlinestore.payload.CartDetailDto;
import com.onlinestore.payload.CartHelp;
import com.onlinestore.repositories.CartDetailsRepo;
import com.onlinestore.repositories.CartRepo;
import com.onlinestore.repositories.UserRepo;
import com.onlinestore.services.CartDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartDetailsServiceImpl implements CartDetailsService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartDetailsRepo cartDetailsRepo;

    public void deleteAllCartDetailsByCartId(int cartId) {
        Cart cart = cartRepo.findById(cartId).orElseThrow();

        List<CartDetails> cartDetailsList = cart.getCartDetails();

        for (CartDetails cartDetails : cartDetailsList) {
            cartDetailsRepo.deleteById(cartDetails.getId());
        }
    }
}
