package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.OrderDto;
import com.example.hotelmanagment.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAllOrders(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        if (page < 1 || size <= 0) {
            return ResponseEntity.badRequest().body("Invalid page or size parameters");
        }

        Page<OrderDto> orders = orderService.getOrdersWithPageable(page - 1, size);

        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("orders", orders.getContent());
        response.put("currentPage", orders.getNumber());
        response.put("totalItems", orders.getTotalElements());
        response.put("totalPages", orders.getTotalPages());

        return ResponseEntity.ok(response);

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOrderById(@PathVariable long id) {
        OrderDto order = orderService.getOrderById(id);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(order);
    }

    @PostMapping("/users/{userId}/rooms/{roomId}")
    public ResponseEntity<?> addOrder(@PathVariable long userId, @PathVariable long roomId, @RequestBody OrderDto orderDto) {
        try {
            orderService.createOrder(orderDto, userId, roomId);

            return ResponseEntity.status(HttpStatus.CREATED).body("Order added successfully");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody OrderDto orderDto) {
        orderService.updateOrder(orderDto);

        return ResponseEntity.ok("order updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable long id) {
        orderService.deleteOrder(id);

        return ResponseEntity.ok("order deleted successfully");
    }

}
