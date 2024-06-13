package com.onlinestore.repositories;

import com.onlinestore.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    @Transactional
    public void testSave_ShouldPersistUserEntity() {
        String email = "john.doe@example.com";
        String password = "password123";
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);

        User savedUser = userRepo.save(user);

        assertNotNull(savedUser.getId());
        assertEquals(email, savedUser.getEmail());
    }

    @Test
    @Transactional
    public void testFindById_ShouldReturnUserForExistingId() {
        String email = "john.doe@example.com";
        String password = "password123";
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        User savedUser = userRepo.save(user);

        int userId = savedUser.getId();

        User foundUser = userRepo.findById(userId).get();

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        assertEquals(email, foundUser.getEmail());
    }

    @Test
    @Transactional
    public void testSave_ShouldUpdateExistingUser() {
        String email = "john.doe@example.com";
        String password = "password123";
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        User savedUser = userRepo.save(user);

        String newEmail = "jane.doe@example.com";
        String newPassword = "newPassword";
        String newEncodedPassword = passwordEncoder.encode(newPassword);

        savedUser.setEmail(newEmail);
        savedUser.setPassword(newEncodedPassword);

        User updatedUser = userRepo.save(savedUser);

        assertEquals(savedUser.getId(), updatedUser.getId());
        assertEquals(newEmail, updatedUser.getEmail());
    }

    @Test
    @Transactional
    public void testDeleteById_ShouldRemoveUser() {
        String email = "john.doe@example.com";
        String password = "password123";
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        User savedUser = userRepo.save(user);

        int userId = savedUser.getId();

        userRepo.deleteById(userId);

        Optional<User> deletedUser = userRepo.findById(userId);

        assertFalse(deletedUser.isPresent());
    }

    @Test
    @Transactional
    public void testFindByEmail_ShouldReturnUserForEmail() {
        String email = "john.doe@example.com";
        String password = "password123";
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        userRepo.save(user);

        User foundUser = userRepo.findByEmail(email);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
    }
}