package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.HotelDto;
import com.example.hotelmanagment.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@AllArgsConstructor
public class HotelController {

    private HotelService hotelService;

    @GetMapping
    public ResponseEntity<?> getAllHotels() {
        List<HotelDto> hotelDtoList = hotelService.getAllHotels();

        if(hotelDtoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(hotelDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelById(@PathVariable("id") int id) {
        HotelDto hotelDto = hotelService.getHotelById(id);

        if(hotelDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(hotelDto);
    }


    @PostMapping
    public ResponseEntity<?> createHotel(@RequestBody HotelDto hotelDto) {
        hotelService.addHotel(hotelDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("hotel created successfully");
    }

    @PutMapping
    public ResponseEntity<?> updateHotel(@RequestBody HotelDto hotelDto) {
        hotelService.updateHotel(hotelDto);

        return ResponseEntity.ok("hotel updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable("id") long id) {
        HotelDto hotelDto = hotelService.getHotelById(id);

        if(hotelDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        hotelService.deleteHotel(id);

        return ResponseEntity.ok("hotel deleted successfully");
    }
}
