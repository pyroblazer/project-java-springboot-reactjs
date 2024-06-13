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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDto CreateUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        List<Role> list = new ArrayList<>();
        list.add(new Role(TotalRoles.ADMIN.name()));
        user.setRole(list);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);

        this.userRepo.save(user);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public SignIn SignIn(SignIn signIn) {
        this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        User user = this.userRepo.findByEmail(signIn.getEmail());
        var jwtToken = jwtService.generateToken(user);
        signIn.setJwt(jwtToken);
        return signIn;
    }
}
