package com.example.hotelmanagment.repository;

import com.example.hotelmanagment.entity.Room;
import com.example.hotelmanagment.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotelId(long hotelId);

    @Query("SELECT r FROM Room r JOIN r.orders o WHERE o.id = :orderId")
    Optional<Room> findByOrderId(@Param("orderId") long orderId);

    @Query("SELECT u FROM Room u JOIN u.reviews r WHERE r.id = :reviewId")
    Optional<Room> findByReviewId(@Param("reviewId") long reviewId);
}
