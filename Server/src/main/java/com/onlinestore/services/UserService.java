package com.onlinestore.services;

import com.onlinestore.payload.SignIn;
import com.onlinestore.payload.UserDto;

public interface UserService {
    public UserDto createUser(UserDto userDto);
    public UserDto getUser(int userId);
    public UserDto updateUser(UserDto userDto, int userId);
    void deleteUser(int userId);
    public SignIn signIn(SignIn signIn);
}
