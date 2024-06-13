package com.onlinestore.repositories;

import com.onlinestore.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {
    Payment findByUserEmail(String userEmail);
}
