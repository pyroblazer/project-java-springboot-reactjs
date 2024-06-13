package com.onlinestore.services;

import com.onlinestore.payload.CartDetailDto;
import com.onlinestore.payload.CartHelp;
import org.springframework.stereotype.Service;

@Service
public interface CartDetailsService {
    public CartDetailDto addProduct(CartHelp cartHelp);
}
