package com.example.hotelmanagment.service;

import com.example.hotelmanagment.dto.UserDto;
import com.example.hotelmanagment.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    void signUp(UserDto userDto);
    void signIn(String email, String password);
    List<UserDto> getAllUsers();
    UserDto getUserById(int id);
    void updateUser(UserDto userDto);
    void deleteUser(int id);
}
