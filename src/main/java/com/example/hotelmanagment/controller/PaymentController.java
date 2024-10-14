package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.PaymentDto;
import com.example.hotelmanagment.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@AllArgsConstructor
public class PaymentController {
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<?> getPayments(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        if (page < 1 || size <= 0) {
            return ResponseEntity.badRequest().body("Invalid page or size parameters");
        }

        Page<PaymentDto> payments = paymentService.getPaymentsWithPageable(page - 1, size);

        if (payments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("payments", payments.getContent());
        response.put("currentPage", payments.getNumber());
        response.put("totalItems", payments.getTotalElements());
        response.put("totalPages", payments.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable("id") long id) {
        PaymentDto paymentDto = paymentService.getPaymentById(id);

        if (paymentDto == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(paymentDto);
    }

    @PostMapping("/orders/{orderId}")
    public ResponseEntity<?> createPayment(@PathVariable("orderId") long orderId, @RequestBody PaymentDto paymentDto) {
        paymentService.savePayment(paymentDto, orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body("payment created successfully");
    }

    @PutMapping
    public ResponseEntity<?> updatePayment(@RequestBody PaymentDto paymentDto) {
        paymentService.updatePayment(paymentDto);

        return ResponseEntity.ok("payment updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable("id") long id) {
        paymentService.deletePaymentById(id);

        return ResponseEntity.ok("payment deleted successfully");
    }



}
