package com.example.hotelmanagment.service;

import com.example.hotelmanagment.dto.RoomDto;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;

import java.util.List;

@Component
public interface RoomService {
    void addRoom(RoomDto roomDto, long hotelId);
    void updateRoom(RoomDto roomDto);
    void deleteRoom(long id);
    List<RoomDto> getAllRooms();
    RoomDto getRoomById(long id);
    List<RoomDto> getAllRoomsByHotelId(long hotelId);
    Page<RoomDto> getRoomsByPageAndSize(int page, int size);
}
