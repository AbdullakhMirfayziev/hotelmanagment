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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/rooms")
@AllArgsConstructor
@Tag(name = "Room", description = "Room management APIs")
public class RoomController {
    private RoomService roomService;
    private HotelService hotelService;
    private UserService userService;

    @Operation(summary = "Get all rooms", description = "Retrieve a paginated list of rooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Invalid page or size parameters")
    })
    @GetMapping
    public ResponseEntity<?> getAllRooms(@Parameter(description = "Page number", example = "1") @RequestParam(defaultValue = "1") int page,
                                         @Parameter(description = "Number of items per page", example = "5") @RequestParam(defaultValue = "5") int size) {
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

    @Operation(summary = "Get room by ID", description = "Retrieve a room by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = RoomDto.class))),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@Parameter(description = "Room ID", example = "1") @PathVariable int id) {
        RoomDto room = roomService.getRoomById(id);

        if (room == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(room);
    }

    @Operation(summary = "Create a new room", description = "Create a new room for a specific hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/hotels/{hotelId}")
    public ResponseEntity<?> createRoom(@Parameter(description = "Hotel ID", example = "1") @PathVariable long hotelId,
                                        @Parameter(description = "Room details") @RequestBody RoomDto roomDto) {
        roomService.addRoom(roomDto, hotelId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Room created successfully");
    }

    @Operation(summary = "Update a room", description = "Update an existing room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @PutMapping
    public ResponseEntity<?> updateRoom(@Parameter(description = "Updated room details") @RequestBody RoomDto roomDto) {
        roomService.updateRoom(roomDto);

        return ResponseEntity.ok("room updated successfully");
    }

    @Operation(summary = "Delete a room", description = "Delete a room by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@Parameter(description = "Room ID", example = "1") @PathVariable long id) {
        RoomDto roomDto = roomService.getRoomById(id);

        if (roomDto == null) {
            return ResponseEntity.notFound().build();
        }

        roomService.deleteRoom(id);

        return ResponseEntity.ok("room deleted successfully");
    }
}
