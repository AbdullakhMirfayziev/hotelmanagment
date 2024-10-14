package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.HotelDto;
import com.example.hotelmanagment.service.HotelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/hotels")
@AllArgsConstructor
@Api(tags = "Hotel Management")
public class HotelController {

    private HotelService hotelService;

    @ApiOperation(value = "Get all hotels", notes = "Retrieves a paginated list of all hotel")
    @GetMapping
    public ResponseEntity<?> getAllHotels(@ApiParam(value = "Page Number", defaultValue = "1") @RequestParam(defaultValue = "1") int page,
                                          @ApiParam(value = "Number of items per page", defaultValue = "5") @RequestParam(defaultValue = "5") int size) {
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

    @ApiOperation(value = "Get hotel by ID", notes = "Retrieves a room by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelById(@ApiParam(value = "Hotel ID", readOnly = true) @PathVariable("id") int id) {
        HotelDto hotelDto = hotelService.getHotelById(id);

        if(hotelDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(hotelDto);
    }

    @ApiOperation(value = "Create a new Hotel", notes = "Create a hotel")
    @PostMapping
    public ResponseEntity<?> createHotel(@ApiParam(value = "Hotel details", readOnly = true) @RequestBody HotelDto hotelDto) {
        hotelService.addHotel(hotelDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("hotel created successfully");
    }

    @ApiOperation(value = "Update a hotel", notes = "Update an existing hotel")
    @PutMapping
    public ResponseEntity<?> updateHotel(@ApiParam(value = "Updated hotel details", required = true) @RequestBody HotelDto hotelDto) {
        hotelService.updateHotel(hotelDto);

        return ResponseEntity.ok("hotel updated successfully");
    }

    @ApiOperation(value = "Delete a hotel", notes = "Delete a hotel by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHotel(@ApiParam(value = "Hotel ID" , required = true) @PathVariable("id") long id) {
        HotelDto hotelDto = hotelService.getHotelById(id);

        if(hotelDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        hotelService.deleteHotel(id);

        return ResponseEntity.ok("hotel deleted successfully");
    }
}
