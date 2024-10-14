package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.UserDto;
import com.example.hotelmanagment.response.AuthenticationRequest;
import com.example.hotelmanagment.response.AuthenticationResponse;
import com.example.hotelmanagment.response.ForgotPasswordRequest;
import com.example.hotelmanagment.response.ResetPasswordRequest;
import com.example.hotelmanagment.service.UserService;
import com.example.hotelmanagment.serviceImpl.EmailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private UserService userService;
    private EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody UserDto user) {
        String token = userService.signUp(user);
        return ResponseEntity.ok("User registered successfully. Please check your email for verification. Token: " + token);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String email, @RequestParam String code) {
        boolean verified = userService.verifyEmail(email, code);
        if (verified) {
            return ResponseEntity.ok("Email verified successfully. Your account is now active.");
        } else {
            return ResponseEntity.badRequest().body("Invalid verification code");
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        emailService.initiatePasswordReset(request.getEmail());
        return ResponseEntity.ok("Password reset link sent to your email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        emailService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Password reset successfully");
    }
}
