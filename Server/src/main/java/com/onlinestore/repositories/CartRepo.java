package com.onlinestore.repositories;


import com.onlinestore.entities.Cart;
import com.onlinestore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepo extends JpaRepository<Cart,Integer> {
   public Cart findByUser(User user);
//   public Cart findByUser_id(Integer Id);
}
