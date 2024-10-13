package com.example.hotelmanagment.serviceImpl;

import com.example.hotelmanagment.dto.HotelDto;
import com.example.hotelmanagment.dto.HotelDtoUtil;
import com.example.hotelmanagment.entity.Hotel;
import com.example.hotelmanagment.entity.Room;
import com.example.hotelmanagment.repository.HotelRepository;
import com.example.hotelmanagment.repository.RoomRepository;
import com.example.hotelmanagment.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {
    private HotelRepository hotelRepository;
    private HotelDtoUtil dtoUtil;
    private RoomRepository roomRepository;

    @Override
    public void addHotel(HotelDto hotelDto) {
        Hotel hotel = dtoUtil.toEntity(hotelDto);

        hotelRepository.save(hotel);
    }

    @Override
    public HotelDto getHotelById(long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new RuntimeException("hotel not found"));

        return dtoUtil.toDto(hotel);
    }

    @Override
    public List<HotelDto> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();


        return dtoUtil.toDto(hotels);
    }

    @Override
    public void updateHotel(HotelDto hotelDto) {
        Hotel hotel = hotelRepository.findById(hotelDto.getId()).orElseThrow(() -> new RuntimeException("hotel not found"));

        hotel.setName(hotelDto.getName());
        hotel.setAddress(hotelDto.getAddress());
        hotel.setPhone(hotelDto.getPhone());
        List<Room> rooms = roomRepository.findByHotelId(hotelDto.getId());
        hotel.setRooms(rooms);

        hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(long id) {
        hotelRepository.deleteById(id);
    }
}
