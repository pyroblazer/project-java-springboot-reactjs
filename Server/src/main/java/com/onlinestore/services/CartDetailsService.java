package com.onlinestore.services;

import com.onlinestore.payload.CartDetailDto;
import com.onlinestore.payload.CartHelp;

public interface CartDetailsService {
    public void deleteAllCartDetailsByCartId(int cartId);
}
