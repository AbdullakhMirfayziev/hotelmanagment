package com.example.hotelmanagment.dto;

import com.example.hotelmanagment.entity.Hotel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HotelDtoUtil {

    public HotelDto toDto(Hotel hotel) {
        HotelDto hotelDto = HotelDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .phone(hotel.getPhone())
                .build();

        return hotelDto;
    }

    public Hotel toEntity(HotelDto hotelDto) {
        Hotel hotel = new Hotel();
        if(hotelDto.getId() != null)
            hotel.setId(hotelDto.getId());
        hotel.setName(hotelDto.getName());
        hotel.setAddress(hotelDto.getAddress());
        hotel.setPhone(hotelDto.getPhone());

        return hotel;
    }

    public List<HotelDto> toDto(List<Hotel> hotels) {
        return hotels.stream().map(this::toDto).toList();
    }

    public List<Hotel> toEntity(List<HotelDto> hotelDtos) {
        return hotelDtos.stream().map(this::toEntity).toList();
    }
}
