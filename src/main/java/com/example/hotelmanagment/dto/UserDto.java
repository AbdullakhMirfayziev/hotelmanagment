package com.example.hotelmanagment.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@Schema(description = "User Data Transfer Object")
public class UserDto {

    @Schema(description = "Unique identifier of the user", example = "1")
    private long id;

    @NotBlank(message = "Full name is required")
    @Length(min = 2, max = 50, message = "Full name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full name can only contain letters and spaces")
    @Schema(description = "Full name of the user", example = "John Doe")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Length(max = 100, message = "Email must not exceed 100 characters")
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+998[0-9]{9}$", message = "Phone number must be in format +998XXXXXXXXX")
    @Schema(description = "Phone number of the user", example = "+998998887766")
    private String phone;

    @NotBlank(message = "Password is required")
    @Length(min = 8, max = 32, message = "Password must be between 8 and 32 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one digit, one lowercase and one uppercase letter"
    )
    @Schema(description = "User's password", example = "Password123")
    private String password;
}