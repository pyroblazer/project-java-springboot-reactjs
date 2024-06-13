package com.onlinestore.services.impl;

import com.onlinestore.payload.CartDetailDto;
import com.onlinestore.payload.CartHelp;
import com.onlinestore.repositories.CartRepo;
import com.onlinestore.repositories.UserRepo;
import com.onlinestore.services.CartDetailsService;
import org.springframework.beans.factory.annotation.Autowired;

public class CartDetailsServiceImpl implements CartDetailsService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Override
    public CartDetailDto addProduct(CartHelp cartHelp) {
        int productId=cartHelp.getProductId();
        int quantity= cartHelp.getQuantity();
        String userEmail= cartHelp.getUserEmail();
        return null;
    }
}
