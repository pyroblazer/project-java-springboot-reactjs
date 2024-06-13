package com.onlinestore.repositories;

import com.onlinestore.entities.Cart;
import com.onlinestore.entities.CartDetails;
import com.onlinestore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailsRepo extends JpaRepository<CartDetails,Integer> {
    public void deleteByProductsAndCart(Product product, Cart cart);
    public CartDetails findByProductsAndCart(Product product, Cart cart);
}
