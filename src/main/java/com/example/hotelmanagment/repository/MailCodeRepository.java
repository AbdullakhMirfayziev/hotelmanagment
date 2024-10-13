package com.example.hotelmanagment.repository;

import com.example.hotelmanagment.entity.MailCode;
import com.example.hotelmanagment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailCodeRepository extends JpaRepository<MailCode, Long> {
    @Query("SELECT m FROM MailCode m WHERE m.isExpired = false AND m.user.id = :userId")
    Optional<MailCode> findByUserIdAndIsExpiredFalse(@Param("userId") long userId);
}
