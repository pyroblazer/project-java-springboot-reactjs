package com.onlinestore.services;

import com.onlinestore.payload.CartDto;
import com.onlinestore.payload.CartHelp;

public interface CartService {
//    CartDto createCart(CartHelp cartHelp);
    CartDto addProductToCart(CartHelp cartHelp);
    CartDto getCart(String userEmail);
    CartDto updateCart(CartDto cartDto) throws Exception;
    void emptyCart(Integer cartId) throws Exception;
    void removeCartDetailFromCart(Integer intCartDetailId, String userEmail);
}
