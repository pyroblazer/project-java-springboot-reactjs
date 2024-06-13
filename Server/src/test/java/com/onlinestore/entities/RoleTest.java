package com.onlinestore.entities;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class RoleTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testCreateRole() {
        String roleName = "ADMIN";
        Role role = new Role(roleName);

        entityManager.persist(role);

        entityManager.flush();

        Role retrievedRole = entityManager.find(Role.class, role.getId());
        assertNotNull(retrievedRole);
        assertEquals(roleName, retrievedRole.getRole());
    }
}