package com.example.hotelmanagment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@Schema(description = "User Data Transfer Object")
public class UserDto {

    @Schema(description = "Unique identifier of the user", example = "1")
    private long id;

    @Schema(description = "Full name of the user", example = "John Doe")
    private String fullName;

    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Phone number of the user", example = "+998998887766")
    private String phone;

    @Schema(description = "User's password", example = "password123")
    private String password;
}