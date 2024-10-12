package com.example.hotelmanagment.dto;

import com.example.hotelmanagment.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDtoUtil {

    public UserDto toDto(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phone(user.getPhoneNumber())
                .build();

        return userDto;
    }

    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhone());
        return user;
    }

    public List<UserDto> toDto(List<User> users) {
        return users.stream().map(this::toDto).toList();
    }

    public List<User> toEntity(List<UserDto> userDtos) {
        return userDtos.stream().map(this::toEntity).toList();
    }
}
