package com.onlinestore.services;

import com.onlinestore.payload.SignIn;
import com.onlinestore.payload.UserDto;

public interface UserService {
    UserDto CreateUser(UserDto userDto);
    SignIn SignIn(SignIn signIn);
}
