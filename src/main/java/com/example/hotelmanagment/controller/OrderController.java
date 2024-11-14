package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.OrderDto;
import com.example.hotelmanagment.exceptions.RoomAlreadyBookedException;
import com.example.hotelmanagment.service.OrderService;
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
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
@Tag(name = "Order Management", description = "Operations pertaining to orders in the application")
public class OrderController {
    private OrderService orderService;

    @Operation(summary = "Get all orders", description = "Retrieves a paginated list of all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of orders",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "204", description = "No orders found"),
            @ApiResponse(responseCode = "400", description = "Invalid page or size parameters")
    })
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

    @Operation(summary = "Get order by ID", description = "Retrieves an order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the order",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getOrderById(@Parameter(description = "Order ID", required = true, example = "1") @PathVariable long id) {
        OrderDto order = orderService.getOrderById(id);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Create a new order", description = "Creates a new order for a user and a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "409", description = "Conflict in creating the order"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/users/{userId}/rooms/{roomId}")
    public ResponseEntity<?> addOrder(@Parameter(description = "User ID", required = true, example = "1")
                                      @PathVariable long userId,    
                                      @Parameter(description = "Room ID", required = true, example = "1")
                                      @PathVariable long roomId,
                                      @Parameter(description = "Order details", required = true)
                                      @RequestBody OrderDto orderDto) {
        try {
            orderService.createOrder(orderDto, userId, roomId);

            return ResponseEntity.status(HttpStatus.CREATED).body("Order added successfully");

        } catch (RoomAlreadyBookedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Update an order", description = "Updates an existing order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PutMapping
    public ResponseEntity<?> updateOrder(@Parameter(description = "Updated order details", required = true)
                                         @RequestBody OrderDto orderDto) {
        orderService.updateOrder(orderDto);

        return ResponseEntity.ok("order updated successfully");
    }

    @Operation(summary = "Delete an order", description = "Deletes an order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Parameter(description = "Order ID", required = true, example = "1")
                                         @PathVariable long id) {
        orderService.deleteOrder(id);

        return ResponseEntity.ok("order deleted successfully");
    }

}
