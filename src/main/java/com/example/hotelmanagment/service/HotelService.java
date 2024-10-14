package com.example.hotelmanagment.service;

import com.example.hotelmanagment.dto.HotelDto;
import com.example.hotelmanagment.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HotelService {
    void addHotel(HotelDto hotelDto);
    HotelDto getHotelById(long id);
    List<HotelDto> getAllHotels();
    void updateHotel(HotelDto hotelDto);
    void deleteHotel(long id);
    Page<HotelDto> getHotels(int size, int page);
}
