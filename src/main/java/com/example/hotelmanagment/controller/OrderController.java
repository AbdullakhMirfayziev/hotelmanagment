package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.OrderDto;
import com.example.hotelmanagment.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();

        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(orders);
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
        orderService.createOrder(orderDto, userId, roomId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Order added successfully");
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