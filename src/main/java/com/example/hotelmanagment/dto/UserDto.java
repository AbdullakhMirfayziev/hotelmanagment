package com.example.hotelmanagment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private long id;
    private String fullName;
    private String email;
    private String phone;
    private String password;
}
