package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.CsvExportUtil;
import com.example.hotelmanagment.dto.HotelDto;
import com.example.hotelmanagment.dto.OrderDto;
import com.example.hotelmanagment.dto.RoomDto;
import com.example.hotelmanagment.dto.UserDto;
import com.example.hotelmanagment.service.HotelService;
import com.example.hotelmanagment.service.OrderService;
import com.example.hotelmanagment.service.RoomService;
import com.example.hotelmanagment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
@Tag(name = "Admin Panel", description = "Operations for exporting data in CSV format")
public class AdminPanelController {
    private UserService userService;
    private CsvExportUtil csvExportUtil;
    private HotelService hotelService;
    private OrderService orderService;
    private RoomService roomService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Operation(summary = "Export users data", description = "Exports all users' data in CSV format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully exported users data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/export/users")
    public ResponseEntity<?> exportUsers() throws IOException {
        List<UserDto> users = userService.getAllUsers();

        List<String> headers = Arrays.asList("ID", "Username", "Email", "Role");
        List<List<String>> data = users.stream()
                .map(user -> Arrays.asList(
                        String.valueOf(user.getId()),
                        user.getFullName(),
                        user.getEmail(), 
                        user.getPhone()
                ))
                .collect(Collectors.toList());

        byte[] csvBytes = csvExportUtil.exportToCsv(headers, data);

        ByteArrayResource resource = new ByteArrayResource(csvBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.csv")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(csvBytes.length)
                .body(resource);
    }

    @Operation(summary = "Export hotels data", description = "Exports all hotels' data in CSV format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully exported hotels data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/export/hotels")
    public ResponseEntity<?> exportHotels() throws IOException {
        List<HotelDto> hotels = hotelService.getAllHotels();

        List<String> headers = Arrays.asList("ID", "Name", "Address", "Phone");
        List<List<String>> data = hotels.stream()
                .map(hotel -> Arrays.asList(
                        String.valueOf(hotel.getId()),
                        hotel.getName(),
                        hotel.getAddress(),
                        hotel.getPhone()
                ))
                .collect(Collectors.toList());

        byte[] csvBytes = csvExportUtil.exportToCsv(headers, data);

        ByteArrayResource resource = new ByteArrayResource(csvBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=hotels.csv")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(csvBytes.length)
                .body(resource);
    }

    @Operation(summary = "Export orders data", description = "Exports all orders' data in CSV format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully exported orders data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/export/orders")
    public ResponseEntity<?> exportOrders() throws IOException {
        List<OrderDto> orders = orderService.getAllOrders();

        List<String> headers = Arrays.asList("ID", "Begin date", "End date", "Room ID");
        List<List<String>> data = orders.stream()
                .map(order -> Arrays.asList(
                        String.valueOf(order.getId()),
                        order.getBeginDate().format(DATE_FORMATTER),
                        order.getEndDate().format(DATE_FORMATTER),
                        String.valueOf(order.getRoomId())
                )).collect(Collectors.toList());

        byte[] csvBytes = csvExportUtil.exportToCsv(headers, data);

        ByteArrayResource resource = new ByteArrayResource(csvBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=orders.csv")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(csvBytes.length)
                .body(resource);
    }

    @GetMapping("/export/rooms")
    public ResponseEntity<?> exportRooms() throws IOException {
        List<RoomDto> rooms = roomService.getAllRooms();

        List<String> headers = Arrays.asList("ID", "Number", "Category", "Price", "Avarage Rating");
        List<List<String>> data = rooms.stream()
                .map(room -> Arrays.asList(
                        String.valueOf(room.getId()),
                        String.valueOf(room.getNumber()),
                        room.getCategory().name(),
                        String.valueOf(room.getPrice()),
                        String.valueOf(room.getAvrgRating())
                        ))
                .collect(Collectors.toList());

        byte[] csvBytes = csvExportUtil.exportToCsv(headers, data);
        ByteArrayResource resource = new ByteArrayResource(csvBytes);

        return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rooms.cvs")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .contentLength(csvBytes.length)
                        .body(resource);
    }

}
