package com.onlinestore.repositories;

import com.onlinestore.entities.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class PaymentRepoTest {

    @Autowired
    private PaymentRepo paymentRepo;

    @Test
    @Transactional
    public void testSave_ShouldPersistPaymentEntity() {
        Payment payment = new Payment();
        payment.setUserEmail("john.doe@example.com");
        payment.setAmount(100.0);

        Payment savedPayment = paymentRepo.save(payment);

        assertNotNull(savedPayment.getId());
        assertEquals(payment.getUserEmail(), savedPayment.getUserEmail());
        assertEquals(payment.getAmount(), savedPayment.getAmount());
    }

    @Test
    @Transactional
    public void testFindById_ShouldReturnPaymentForExistingId() {
        Payment payment = new Payment();
        payment.setUserEmail("john.doe@example.com");
        payment.setAmount(100.0);
        Payment savedPayment = paymentRepo.save(payment);

        int paymentId = savedPayment.getId();

        Payment foundPayment = paymentRepo.findById(paymentId).get();

        assertNotNull(foundPayment);
        assertEquals(paymentId, foundPayment.getId());
        assertEquals(payment.getUserEmail(), foundPayment.getUserEmail());
        assertEquals(payment.getAmount(), foundPayment.getAmount());
    }

    @Test
    @Transactional
    public void testSave_ShouldUpdateExistingPayment() {
        Payment payment = new Payment();
        payment.setUserEmail("john.doe@example.com");
        payment.setAmount(100.0);
        Payment savedPayment = paymentRepo.save(payment);

        savedPayment.setUserEmail("jane.doe@example.com");
        savedPayment.setAmount(200.0);
        Payment updatedPayment = paymentRepo.save(savedPayment);

        assertEquals(savedPayment.getId(), updatedPayment.getId());
        assertEquals("jane.doe@example.com", updatedPayment.getUserEmail());
        assertEquals(200.0, updatedPayment.getAmount());
    }

    @Test
    @Transactional
    public void testDeleteById_ShouldRemovePayment() {
        Payment payment = new Payment();
        payment.setUserEmail("john.doe@example.com");
        payment.setAmount(100.0);
        Payment savedPayment = paymentRepo.save(payment);

        int paymentId = savedPayment.getId();

        paymentRepo.deleteById(paymentId);

        Optional<Payment> deletedPayment = paymentRepo.findById(paymentId);

        assertFalse(deletedPayment.isPresent());
    }

    @Test
    @Transactional
    public void testFindByUserEmail_ShouldReturnPaymentForUserEmail() {
        String userEmail = "john.doe@example.com";
        Payment payment = new Payment();
        payment.setUserEmail(userEmail);
        payment.setAmount(100.0);
        paymentRepo.save(payment);

        Payment foundPayment = paymentRepo.findByUserEmail(userEmail);

        assertNotNull(foundPayment);
        assertEquals(userEmail, foundPayment.getUserEmail());
    }
}