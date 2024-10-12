package com.example.hotelmanagment.service;

import com.example.hotelmanagment.dto.HotelDto;
import com.example.hotelmanagment.entity.Hotel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HotelService {
    void addHotel(HotelDto hotelDto);
    HotelDto getHotelById(long id);
    List<HotelDto> getAllHotels();
    void updateHotel(HotelDto hotelDto);
    void deleteHotel(long id);
}
