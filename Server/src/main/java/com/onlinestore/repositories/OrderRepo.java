package com.onlinestore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinestore.entities.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Integer>{
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
    List<Order> findOrdersByUserId(@Param("userId") int userId);

    @Query("SELECT o FROM Order o WHERE o.user.email = :userEmail")
    List<Order> findOrdersByUserEmail(@Param("userEmail") String userEmail);
}
