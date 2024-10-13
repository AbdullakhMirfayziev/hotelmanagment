package com.example.hotelmanagment.repository;

import com.example.hotelmanagment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN u.orders o WHERE o.id = :orderId")
    Optional<User> findByOrderId(@Param("orderId") long orderId);

    @Query("SELECT u FROM User u JOIN u.reviews r WHERE r.id = :reviewId")
    Optional<User> findByReviewId(@Param("reviewId") long reviewId);

    Optional<User> findByEmail(String email);
}
