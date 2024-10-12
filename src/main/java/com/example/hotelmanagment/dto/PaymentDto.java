package com.example.hotelmanagment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PaymentDto {
    private long id;
    private double amount;
    private LocalDateTime createdAt;
}
