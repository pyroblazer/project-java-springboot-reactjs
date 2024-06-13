package com.onlinestore.controllers;

import com.onlinestore.payload.SignIn;
import com.onlinestore.payload.UserDto;
import com.onlinestore.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        UserDto userDto = new UserDto(1, "John Doe", "johndoe@example.com", "password", "USER");
        UserDto createdUserDto = new UserDto(userDto.getId(), userDto.getName(), userDto.getEmail(), null, userDto.getRole());

        when(userService.createUser(userDto)).thenReturn(createdUserDto);

        ResponseEntity<UserDto> response = userController.CreateUser(userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdUserDto, response.getBody());
        verify(userService, times(1)).createUser(userDto);
    }

    @Test
    public void testSignIn() {
        SignIn signIn = new SignIn("johndoe@example.com", "password", null);
        SignIn successfulSignIn = new SignIn(signIn.getEmail(), null, null);

        when(userService.signIn(signIn)).thenReturn(successfulSignIn);

        ResponseEntity<SignIn> response = userController.CreateUser(signIn); // Reused method name for testing

        assertEquals(successfulSignIn.getEmail(), response.getBody().getEmail());
        assertNull(response.getBody().getPassword());
        verify(userService, times(1)).signIn(signIn);
    }
}