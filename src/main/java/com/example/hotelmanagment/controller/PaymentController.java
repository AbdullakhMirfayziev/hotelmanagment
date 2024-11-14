package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.PaymentDto;
import com.example.hotelmanagment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@AllArgsConstructor
@Tag(name = "Payment Management", description = "Operations pertaining to payments in the application")
public class PaymentController {
    private PaymentService paymentService;

    @Operation(summary = "Get all payments", description = "Retrieves a paginated list of all payments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of payments",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "No payments found"),
            @ApiResponse(responseCode = "400", description = "Invalid page or size parameters")
    })
    @GetMapping
    public ResponseEntity<?> getPayments(
            @Parameter(description = "Page number (1-indexed)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Number of items per page", example = "5")
            @RequestParam(defaultValue = "5") int size) {
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

    @Operation(summary = "Get payment by ID", description = "Retrieves a payment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the payment",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDto.class))),
            @ApiResponse(responseCode = "204", description = "Payment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentById(
            @Parameter(description = "Payment ID", required = true, example = "1")
            @PathVariable("id") long id) {
        PaymentDto paymentDto = paymentService.getPaymentById(id);

        if (paymentDto == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(paymentDto);
    }

    @Operation(summary = "Create a new payment", description = "Creates a new payment for an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/orders/{orderId}")
    public ResponseEntity<?> createPayment(
            @Parameter(description = "Order ID", required = true, example = "1")
            @PathVariable("orderId") long orderId,
            @Parameter(description = "Payment details", required = true)
            @RequestBody PaymentDto paymentDto) {
        paymentService.savePayment(paymentDto, orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Payment created successfully");
    }

    @Operation(summary = "Update a payment", description = "Updates an existing payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PutMapping
    public ResponseEntity<?> updatePayment(
            @Parameter(description = "Updated payment details", required = true)
            @RequestBody PaymentDto paymentDto) {
        paymentService.updatePayment(paymentDto);

        return ResponseEntity.ok("Payment updated successfully");
    }

    @Operation(summary = "Delete a payment", description = "Deletes a payment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(
            @Parameter(description = "Payment ID", required = true, example = "1")
            @PathVariable("id") long id) {
        paymentService.deletePaymentById(id);

        return ResponseEntity.ok("Payment deleted successfully");
    }



}
