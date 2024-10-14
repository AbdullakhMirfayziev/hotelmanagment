package com.example.hotelmanagment.service;

import com.example.hotelmanagment.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderService {
    void createOrder(OrderDto orderDto, long userId, long roomId);
    void updateOrder(OrderDto orderDto);
    void deleteOrder(long id);
    List<OrderDto> getAllOrders();
    OrderDto getOrderById(long id);
    OrderDto getOrderByRoomId(long roomId);
    List<OrderDto> getOrderByUserId(long userId);
    Page<OrderDto> getOrdersWithPageable(int page, int size);
}
