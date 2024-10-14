package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.RoomDto;
import com.example.hotelmanagment.service.HotelService;
import com.example.hotelmanagment.service.RoomService;
import com.example.hotelmanagment.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/rooms")
@AllArgsConstructor
public class RoomController {
    private RoomService roomService;
    private HotelService hotelService;
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllRooms(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        if (page < 1 || size <= 0) {
            return ResponseEntity.badRequest().body("Invalid page or size parameters");
        }

        Page<RoomDto> roomPage = roomService.getRoomsByPageAndSize(page - 1, size);

        if (roomPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Create a custom response object
        Map<String, Object> response = new HashMap<>();
        response.put("rooms", roomPage.getContent());
        response.put("currentPage", roomPage.getNumber());
        response.put("totalItems", roomPage.getTotalElements());
        response.put("totalPages", roomPage.getTotalPages());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable int id) {
        RoomDto room = roomService.getRoomById(id);

        if (room == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(room);
    }

    @PostMapping("/hotels/{hotelId}")
    public ResponseEntity<?> createRoom(@PathVariable long hotelId, @RequestBody RoomDto roomDto) {
        roomService.addRoom(roomDto, hotelId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Room created successfully");
    }

    @PutMapping
    public ResponseEntity<?> updateRoom(@RequestBody RoomDto roomDto) {
        roomService.updateRoom(roomDto);

        return ResponseEntity.ok("room updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable long id) {
        RoomDto roomDto = roomService.getRoomById(id);

        if (roomDto == null) {
            return ResponseEntity.notFound().build();
        }

        roomService.deleteRoom(id);

        return ResponseEntity.ok("room deleted successfully");
    }
}
