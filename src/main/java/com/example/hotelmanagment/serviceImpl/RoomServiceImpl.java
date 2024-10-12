package com.example.hotelmanagment.serviceImpl;

import com.example.hotelmanagment.dto.RoomDto;
import com.example.hotelmanagment.dto.RoomDtoUtil;
import com.example.hotelmanagment.entity.Hotel;
import com.example.hotelmanagment.entity.Review;
import com.example.hotelmanagment.entity.Room;
import com.example.hotelmanagment.repository.HotelRepository;
import com.example.hotelmanagment.repository.ReviewRepository;
import com.example.hotelmanagment.repository.RoomRepository;
import com.example.hotelmanagment.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;
    private HotelRepository hotelRepository;
    private RoomDtoUtil dtoUtil;
    private ReviewRepository reviewRepository;

    @Override
    public void addRoom(RoomDto roomDto, long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("hotel not found"));

        // adding room to hotel
        Room room = dtoUtil.toEntity(roomDto);
        hotel.addRoom(room);

        hotelRepository.save(hotel);
    }

    @Override
    public void updateRoom(RoomDto roomDto) {
        Room room = roomRepository.findById(roomDto.getId()).orElseThrow(() -> new RuntimeException("room not found"));

        room.setCategory(roomDto.getCategory());
        room.setPrice(roomDto.getPrice());
        room.setNumber(roomDto.getNumber());

        //setting hotel to the room
        Hotel hotel = hotelRepository.findByRoomId(room.getId()).orElseThrow(() -> new RuntimeException("hotel not found"));
        room.setHotel(hotel);

        // setting reviews to the room
        List<Review> reviews = reviewRepository.findByRoomId(room.getId());
        room.setReviews(reviews);

        roomRepository.save(room);
    }

    @Override
    public void deleteRoom(long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public List<RoomDto> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();

        return dtoUtil.toDto(rooms);
    }

    @Override
    public RoomDto getRoomById(long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("room not found"));

        return dtoUtil.toDto(room);
    }

    @Override
    public List<RoomDto> getAllRoomsByHotelId(long hotelId) {
        List<Room> rooms = roomRepository.findByHotelId(hotelId);

        return dtoUtil.toDto(rooms);
    }
}
