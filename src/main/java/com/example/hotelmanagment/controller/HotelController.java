package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.HotelDto;
import com.example.hotelmanagment.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/hotels")
@AllArgsConstructor
@Tag(name = "Hotel Management", description = "APIs for managing hotels")
public class HotelController {

    private HotelService hotelService;

    @Operation(summary = "Get all hotels", description = "Retrieves a paginated list of all hotels")
    @GetMapping
    public ResponseEntity<?> getAllHotels(
            @Parameter(description = "Page Number", example = "1") @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Number of items per page", example = "5") @RequestParam(defaultValue = "5") int size) {
        if (page < 1 || size <= 0) {
            return ResponseEntity.badRequest().body("Invalid page or size parameters");
        }

        Page<HotelDto> hotels = hotelService.getHotels(page - 1, size);

        if(hotels.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("hotels", hotels.getContent());
        response.put("currentPage", hotels.getNumber());
        response.put("totalItems", hotels.getTotalElements());
        response.put("totalPages", hotels.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get hotel by ID", description = "Retrieves a hotel by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelById(@Parameter(description = "Hotel ID") @PathVariable("id") int id) {
        HotelDto hotelDto = hotelService.getHotelById(id);

        if(hotelDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(hotelDto);
    }

    @Operation(summary = "Create a new Hotel", description = "Create a hotel")
    @PostMapping
    public ResponseEntity<?> createHotel(@Parameter(description = "Hotel details") @RequestBody HotelDto hotelDto) {
        hotelService.addHotel(hotelDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("hotel created successfully");
    }

    @Operation(summary = "Update a hotel", description = "Update an existing hotel")
    @PutMapping
    public ResponseEntity<?> updateHotel(@Parameter(description = "Updated hotel details") @RequestBody HotelDto hotelDto) {
        hotelService.updateHotel(hotelDto);

        return ResponseEntity.ok("hotel updated successfully");
    }

    @Operation(summary = "Delete a hotel", description = "Delete a hotel by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHotel(@Parameter(description = "Hotel ID") @PathVariable("id") long id) {
        HotelDto hotelDto = hotelService.getHotelById(id);

        if(hotelDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        hotelService.deleteHotel(id);

        return ResponseEntity.ok("hotel deleted successfully");
    }
}