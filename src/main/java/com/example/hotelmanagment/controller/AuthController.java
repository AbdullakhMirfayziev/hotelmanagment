package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.UserDto;
import com.example.hotelmanagment.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private UserService userService;

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
}
