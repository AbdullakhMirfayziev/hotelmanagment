package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.RoomDto;
import com.example.hotelmanagment.service.HotelService;
import com.example.hotelmanagment.service.RoomService;
import com.example.hotelmanagment.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@AllArgsConstructor
public class RoomController {
    private RoomService roomService;
    private HotelService hotelService;
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllRooms() {
        List<RoomDto> rooms = roomService.getAllRooms();

        if (rooms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(rooms);
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
