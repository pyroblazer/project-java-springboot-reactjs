package com.onlinestore.repositories;

import com.onlinestore.entities.Cart;
import com.onlinestore.entities.CartDetails;
import com.onlinestore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartDetailsRepo extends JpaRepository<CartDetails, Integer> {
    @Query("SELECT cd FROM CartDetails cd WHERE cd.product.id = :productId AND cd.cart.id = :cartId")
    CartDetails findByProductIdAndCartId(int productId, int cartId);
    CartDetails findByProductAndCart(Product product, Cart cart);
}
