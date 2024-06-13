package com.onlinestore.services.impl;

import com.onlinestore.config.JwtService;
import com.onlinestore.entities.*;
import com.onlinestore.payload.ProductDto;
import com.onlinestore.payload.SignIn;
import com.onlinestore.payload.UserDto;
import com.onlinestore.repositories.UserRepo;
import com.onlinestore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        List<Role> list = new ArrayList<>();
        list.add(new Role(TotalRoles.ADMIN.name()));
        user.setRoles(list);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);

        this.userRepo.save(user);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUser(int userId) {
        User save = this.userRepo.findById(userId).orElseThrow();
        return this.modelMapper.map(save, UserDto.class);
    };

    @Override
    public UserDto updateUser(UserDto userDto, int userId) {
        User existingUser = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPassword(userDto.getPassword());

        userRepo.save(existingUser);

        return this.modelMapper.map(existingUser,UserDto.class);
    };

    @Override
    public void deleteUser(int userId) {
        this.userRepo.deleteById(userId);
    }

    @Override
    public SignIn signIn(SignIn signIn) {
        this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        User user = this.userRepo.findByEmail(signIn.getEmail());
        var jwtToken = jwtService.generateToken(user);
        signIn.setJwt(jwtToken);
        return signIn;
    }
}
