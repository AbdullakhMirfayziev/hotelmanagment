package com.example.hotelmanagment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Schema(description = "Payment Data Transfer Object")
public class PaymentDto {
    @Schema(description = "Unique identifier of the payment", example = "1")
    private long id;

    @Schema(description = "Amount of the payment", example = "100.00")
    private double amount;

    @Schema(description = "Date of the payment", example = "2023-07-01")
    private LocalDateTime createdAt;
}
