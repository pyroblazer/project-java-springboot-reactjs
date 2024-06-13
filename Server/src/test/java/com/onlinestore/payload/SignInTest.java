package com.onlinestore.payload;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class SignInTest {

    @Test
    public void testSignIn() {
        String emailPlaceholder = "user@example.com";
        String passwordPlaceholder = "password123";
        String jwtPlaceholder = "eyJhbGciOiJIUzI1NiJ9...";

        SignIn signIn = new SignIn(emailPlaceholder, passwordPlaceholder, jwtPlaceholder);

        assertEquals(emailPlaceholder, signIn.getEmail());
        assertEquals(passwordPlaceholder, signIn.getPassword());
        assertEquals(jwtPlaceholder, signIn.getJwt());

        String newEmail = "updated@example.com";
        signIn.setEmail(newEmail);
        assertEquals(newEmail, signIn.getEmail());
    }
}
