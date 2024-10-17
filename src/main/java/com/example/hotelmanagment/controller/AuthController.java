package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.UserDto;
import com.example.hotelmanagment.response.AuthenticationRequest;
import com.example.hotelmanagment.response.AuthenticationResponse;
import com.example.hotelmanagment.response.ForgotPasswordRequest;
import com.example.hotelmanagment.response.ResetPasswordRequest;
import com.example.hotelmanagment.service.UserService;
import com.example.hotelmanagment.serviceImpl.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(name = "Auth Management", description = "APIs for managing auth")
public class AuthController {

    private UserService userService;
    private EmailService emailService;

    @Operation(summary = "Sign up a new user", description = "Register a new user and send verification email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody UserDto user) {
        String token = userService.signUp(user);
        return ResponseEntity.ok("User registered successfully. Please check your email for verification. Token: " + token);
    }


    @Operation(summary = "Verify user email", description = "Verify user email using the provided verification code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email verified successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid verification code")
    })
    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String email, @RequestParam String code) {
        boolean verified = userService.verifyEmail(email, code);
        if (verified) {
            return ResponseEntity.ok("Email verified successfully. Your account is now active.");
        } else {
            return ResponseEntity.badRequest().body("Invalid verification code");
        }
    }

    @Operation(summary = "Authenticate user", description = "Authenticate user and return JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @Operation(summary = "Initiate forgot password process", description = "Send password reset link to user's email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset link sent",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        emailService.initiatePasswordReset(request.getEmail());
        return ResponseEntity.ok("Password reset link sent to your email");
    }

    @Operation(summary = "Reset user password", description = "Reset user password using the provided token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successful",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid or expired token")
    })
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        emailService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Password reset successfully");
    }

    @Operation(summary = "Delete user", description = "Delete user from database with ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@Parameter(description = "User ID", required = true, example = "1")
                                             @PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok("User deleted successfully");
    }

    @Operation(summary = "Update user", description = "Updates an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto) {
        userService.updateUser(userDto);

        return ResponseEntity.ok("User updated successfully");
    }


}
