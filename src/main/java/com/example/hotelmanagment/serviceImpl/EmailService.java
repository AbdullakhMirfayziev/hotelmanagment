package com.example.hotelmanagment.serviceImpl;

import com.example.hotelmanagment.entity.User;
import com.example.hotelmanagment.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public void sendVerificationEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Email Verification");
        message.setText("Your verification code is: " + code + "\n" + "Don't give this code to anyone!");
        mailSender.send(message);
    }

    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = generateResetToken();
        user.setResetToken(token);
        user.setResetTokenExpiryDate(LocalDateTime.now().plusHours(24));
        userRepository.save(user);

        // Send email with reset link
        String resetLink = "http://" + "some_domain.com" + "/reset-password?token=" + token;
        sendPasswordResetEmail(user.getEmail(), resetLink);
    }

    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired reset token"));

        if (user.getResetTokenExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token has expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiryDate(null);
        userRepository.save(user);
    }

    private String generateResetToken() {
        return UUID.randomUUID().toString();
    }

    public void sendPasswordResetEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the link below:\n" + resetLink);
        mailSender.send(message);
    }

}
