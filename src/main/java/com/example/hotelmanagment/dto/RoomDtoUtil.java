package com.example.hotelmanagment.dto;

import com.example.hotelmanagment.entity.Room;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomDtoUtil {

    public RoomDto toDto(Room room) {
        RoomDto roomDto = RoomDto.builder()
                .id(room.getId())
                .price(room.getPrice())
                .category(room.getCategory())
                .number(room.getNumber())
                .build();

        return roomDto;
    }

    public Room toEntity(RoomDto roomDto) {
        Room room = new Room();
        room.setId(roomDto.getId());
        room.setPrice(roomDto.getPrice());
        room.setCategory(roomDto.getCategory());
        room.setNumber(roomDto.getNumber());

        return room;
    }

    public List<RoomDto> toDto(List<Room> rooms) {
        return rooms.stream().map(this::toDto).toList();
    }

    public List<Room> toEntity(List<RoomDto> roomDtos) {
        return roomDtos.stream().map(this::toEntity).toList();
    }

}
