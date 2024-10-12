package com.example.hotelmanagment.dto;

import com.example.hotelmanagment.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentDtoUtil {

    public PaymentDto toDto(Payment payment) {
        PaymentDto reviewDto = PaymentDto.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .createdAt(payment.getCreatedAt())
                .build();

        return reviewDto;
    }

    public Payment toEntity(PaymentDto paymentDto) {
        Payment payment = new Payment();
        payment.setId(paymentDto.getId());
        payment.setAmount(paymentDto.getAmount());
        payment.setCreatedAt(paymentDto.getCreatedAt());

        return payment;
    }

    public List<PaymentDto> toDto(List<Payment> payments) {
        return payments.stream().map(this::toDto).toList();
    }

    public List<Payment> toEntity(List<PaymentDto> paymentDtos) {
        return paymentDtos.stream().map(this::toEntity).toList();
    }
}
