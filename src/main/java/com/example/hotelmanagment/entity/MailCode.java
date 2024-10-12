package com.example.hotelmanagment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "mail_code")
@Getter
@Setter
public class MailCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "verification_code", length = 4)
    private String verificationCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expired")
    private boolean isExpired;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name  = "user_id")
    private User user;
}
