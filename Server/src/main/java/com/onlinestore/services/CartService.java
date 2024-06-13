package com.onlinestore.services;

import com.onlinestore.payload.CartDto;
import com.onlinestore.payload.CartHelp;

public interface CartService {
    CartDto CreateCart(CartHelp cartHelp);
    CartDto addProductToCart(CartHelp cartHelp);
    CartDto GetCart(String userEmail);
    void RemoveById(Integer ProductId,String userEmail);
}
