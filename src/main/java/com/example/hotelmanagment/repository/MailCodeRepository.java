package com.example.hotelmanagment.repository;

import com.example.hotelmanagment.entity.MailCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailCodeRepository extends JpaRepository<MailCode, Long> {
}
