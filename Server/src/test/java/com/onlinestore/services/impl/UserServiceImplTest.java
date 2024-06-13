package com.onlinestore.services.impl;

import com.onlinestore.config.JwtService;
import com.onlinestore.entities.Cart;
import com.onlinestore.entities.Role;
import com.onlinestore.entities.TotalRoles;
import com.onlinestore.entities.User;
import com.onlinestore.payload.SignIn;
import com.onlinestore.payload.UserDto;
import com.onlinestore.repositories.UserRepo;
import com.onlinestore.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testCreateUser_ShouldCreateUserAndReturnUserDto() throws Exception {
        UserDto userDto = UserDto.builder()
                .id(1)
                .name("John Doe")
                .email("john.doe@example.com")
                .password("password123")
                .role("USER")
                .build();

        User savedUser = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
        Cart cart = new Cart();
        cart.setUser(savedUser);
        savedUser.setCart(cart);
        savedUser.setRoles(Collections.singletonList(new Role(TotalRoles.USER.name()))); // Assuming default role

        Mockito.when(modelMapper.map(userDto, User.class)).thenReturn(savedUser);
        Mockito.when(userRepo.save(savedUser)).thenReturn(savedUser);
        Mockito.when(modelMapper.map(savedUser, UserDto.class)).thenReturn(userDto);

        UserDto createdUserDto = userService.createUser(userDto);

        assertEquals(userDto.getName(), createdUserDto.getName());
        assertEquals(userDto.getEmail(), createdUserDto.getEmail());
    }

    @Test
    public void testUpdateUser_ShouldUpdateUserAndReturnUserDto() throws Exception {
        int userId = 1;
        String updatedName = "Jane Doe";
        String updatedEmail = "jane.doe@example.com";

        UserDto existingUserDto = UserDto.builder()
                .id(userId)
                .name("John Doe")
                .email("john.doe@example.com")
                .password("password123")
                .role("USER")
                .build();

        UserDto updateDto = UserDto.builder()
                .id(userId)
                .name(updatedName)
                .email(updatedEmail)
                .build();

        User existingUser = User.builder()
                .id(userId)
                .name(existingUserDto.getName())
                .email(existingUserDto.getEmail())
                .build();

        User updatedUser = User.builder()
                .id(userId)
                .name(updatedName)
                .email(updatedEmail)
                .build();

        Mockito.when(userRepo.findById(userId)).thenReturn(Optional.of(existingUser));
        Mockito.when(modelMapper.map(updateDto, User.class)).thenReturn(updatedUser);
        Mockito.when(userRepo.save(updatedUser)).thenReturn(updatedUser);
        Mockito.when(modelMapper.map(updatedUser, UserDto.class)).thenReturn(updateDto);

        UserDto returnedUserDto = userService.updateUser(updateDto, userId);

        assertEquals(userId, returnedUserDto.getId());
        assertEquals(updatedName, returnedUserDto.getName());
        assertEquals(updatedEmail, returnedUserDto.getEmail());
    }

    @Test
    public void testUpdateUser_ShouldThrowExceptionForNonexistentUser() {
        int userId = 1;
        UserDto updateDto = UserDto.builder().id(userId).build();

        Mockito.when(userRepo.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(updateDto, userId));
    }

    @Test
    public void testDeleteUser_ShouldDeleteUser() throws Exception {
        int userId = 1;

        Mockito.doNothing().when(userRepo).deleteById(userId); // Mocks deletion without returning a value

        userService.deleteUser(userId);

        Mockito.verify(userRepo).deleteById(userId);
    }

    @Test
    public void testSignIn_ShouldReturnJwtTokenForValidCredentials() throws Exception {
        String email = "john.doe@example.com";
        String password = "password123";
        String expectedToken = "eyJhbGciNiIsInR5...";

        Mockito.when(authenticationManager.authenticate(any())).thenReturn(null);

        User user = User.builder().email(email).build();
        Mockito.when(userRepo.findByEmail(email)).thenReturn(user);

        Mockito.when(jwtService.generateToken(user)).thenReturn(expectedToken);

        SignIn signIn = SignIn.builder()
                .email(email)
                .password(password)
                .build();
        SignIn signedInUser = userService.signIn(signIn);

        assertEquals(expectedToken, signedInUser.getJwt());
    }
}
