package com.example.hotelmanagment.repository;

import com.example.hotelmanagment.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(long id);
    List<Order> findByRoomId(long id);
}
