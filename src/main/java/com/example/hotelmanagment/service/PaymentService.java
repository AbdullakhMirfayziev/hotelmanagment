package com.example.hotelmanagment.service;

import com.example.hotelmanagment.dto.PaymentDto;
import com.example.hotelmanagment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PaymentService {
    void savePayment(PaymentDto paymentDto, long orderId);
    void updatePayment(PaymentDto paymentDto);
    void deletePaymentById(long id);
    List<PaymentDto> getAllPayments();
    PaymentDto getPaymentById(long id);
    PaymentDto getPaymentByOrderId(long orderId);
    Page<PaymentDto> getPaymentsWithPageable(int page, int size);
}
