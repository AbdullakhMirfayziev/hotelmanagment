package com.example.hotelmanagment.serviceImpl;

import com.example.hotelmanagment.dto.UserDto;
import com.example.hotelmanagment.dto.UserDtoUtil;
import com.example.hotelmanagment.entity.*;
import com.example.hotelmanagment.repository.MailCodeRepository;
import com.example.hotelmanagment.repository.OrderRepository;
import com.example.hotelmanagment.repository.ReviewRepository;
import com.example.hotelmanagment.repository.UserRepository;
import com.example.hotelmanagment.response.AuthenticationRequest;
import com.example.hotelmanagment.response.AuthenticationResponse;
import com.example.hotelmanagment.jwt.JwtService;
import com.example.hotelmanagment.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserDtoUtil dtoUtil;
    private OrderRepository orderRepository;
    private ReviewRepository reviewRepository;
    private final JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    private MailCodeRepository mailCodeRepository;
    private EmailService emailService;
    private CustomUserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;


    @Override
    public String signUp(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail()).orElse(null);

        if (user != null) {
            throw new RuntimeException("User already exists");
        }

        user = dtoUtil.toEntity(userDto);

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = new Role();
        role.setRole("ROLE_USER");
        role.setUser(user);
        user.setRole(role);

        String token = jwtService.generateToken(user);

        user.setVerificationToken(token);
        user.setVerificationTokenExpiryDate(LocalDateTime.now().plusHours(24));

        userRepository.save(user);


        String verificationCode = generateVerificationCode();

        // Create and save MailCode
        MailCode mailCode = new MailCode();
        mailCode.setVerificationCode(verificationCode);
        mailCode.setCreatedAt(LocalDateTime.now());
        mailCode.setExpired(false);
        mailCode.setUser(user);
        mailCodeRepository.save(mailCode);

        // Send verification email
        emailService.sendVerificationEmail(user.getEmail(), verificationCode);

        return token;

    }

    @Override
    public void signIn(String email, String password) {

    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return dtoUtil.toDto(users);
    }

    @Override
    public UserDto getUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));

        return dtoUtil.toDto(user);
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new RuntimeException("user not found"));

        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());

        // setting reviews to the user
        List<Review> reviews = reviewRepository.findByUserId(userDto.getId());
        user.setReviews(reviews);

        // setting order to the user
        List<Order> orders = orderRepository.findByUserId(userDto.getId());
        user.setOrders(orders);

        userRepository.save(user);

    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }


    @Override
    public boolean verifyEmail(String email, String code) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        MailCode mailCode = mailCodeRepository.findByUserIdAndIsExpiredFalse(user.getId())
                .orElseThrow(() -> new RuntimeException("Verification code not found or expired"));

        if (mailCode.getVerificationCode().equals(code)) {
            user.setEnabled(true);
            userRepository.save(user);
            mailCode.setExpired(true);
            mailCodeRepository.save(mailCode);
            return true;
        }

        return false;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    private String generateVerificationCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        return String.valueOf(code);
    }
}
