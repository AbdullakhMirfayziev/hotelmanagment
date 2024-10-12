package com.example.hotelmanagment.dto;

import com.example.hotelmanagment.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDtoUtil {

    public OrderDto toDto(Order order) {
        OrderDto orderDto = OrderDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .price(order.getPrice())
                .days(order.getDays())
                .hotelId(order.getRoom().getHotel().getId())
                .roomId(order.getRoom().getId())
                .build();

        return orderDto;
    }

    public Order toEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setOrderDate(orderDto.getOrderDate());
        order.setPrice(orderDto.getPrice());
        order.setDays(orderDto.getDays());

        return order;
    }

    public List<OrderDto> toDto(List<Order> orders) {
        return orders.stream().map(this::toDto).toList();
    }

    public List<Order> toEntity(List<OrderDto> orderDtos) {
        return orderDtos.stream().map(this::toEntity).toList();
    }

}
