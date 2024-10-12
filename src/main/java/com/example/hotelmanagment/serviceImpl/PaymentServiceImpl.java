package com.example.hotelmanagment.serviceImpl;

import com.example.hotelmanagment.dto.PaymentDto;
import com.example.hotelmanagment.dto.PaymentDtoUtil;
import com.example.hotelmanagment.entity.Order;
import com.example.hotelmanagment.entity.Payment;
import com.example.hotelmanagment.repository.OrderRepository;
import com.example.hotelmanagment.repository.PaymentRepository;
import com.example.hotelmanagment.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;
    private OrderRepository orderRepository;
    private PaymentDtoUtil dtoUtil;

    @Override
    public void savePayment(PaymentDto paymentDto, long orderId) {
        Payment payment = new Payment();
        payment.setAmount(paymentDto.getAmount());
        payment.setCreatedAt(LocalDateTime.now());

        // setting order to the payment
        Order order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));
        payment.setOrder(order);

        paymentRepository.save(payment);
    }

    @Override
    public void updatePayment(PaymentDto paymentDto) {
        Payment payment = paymentRepository.findById(paymentDto.getId()).orElseThrow(()->new RuntimeException("Payment not found"));

        payment.setAmount(paymentDto.getAmount());

        paymentRepository.save(payment);
    }

    @Override
    public void deletePaymentById(long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return List.of();
    }

    @Override
    public PaymentDto getPaymentById(long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(()->new RuntimeException("Payment not found"));

        return dtoUtil.toDto(payment);
    }

    @Override
    public PaymentDto getPaymentByOrderId(long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(()->new RuntimeException("Payment not found"));

        return dtoUtil.toDto(payment);
    }
}
