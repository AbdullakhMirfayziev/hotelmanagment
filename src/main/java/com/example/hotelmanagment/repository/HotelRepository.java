package com.example.hotelmanagment.repository;

import com.example.hotelmanagment.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h JOIN h.rooms r WHERE r.id = :roomId")
    Optional<Hotel> findByRoomId(@Param("roomId") Long roomId);
}
