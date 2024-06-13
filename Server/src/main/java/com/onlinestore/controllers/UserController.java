package com.onlinestore.controllers;

import com.onlinestore.payload.SignIn;
import com.onlinestore.payload.UserDto;
import com.onlinestore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> CreateUser(@RequestBody UserDto userDto) {

        UserDto userDto1 = this.userService.createUser(userDto);

        return new ResponseEntity<>(userDto1, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/signin")
    public ResponseEntity<SignIn> CreateUser(@RequestBody SignIn signIn) {

        SignIn signIn1 = this.userService.signIn(signIn);
        return new ResponseEntity<>(signIn1, HttpStatusCode.valueOf(200));
    }
}
