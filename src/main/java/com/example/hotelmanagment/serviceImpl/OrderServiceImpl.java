package com.example.hotelmanagment.serviceImpl;

import com.example.hotelmanagment.dto.OrderDto;
import com.example.hotelmanagment.dto.OrderDtoUtil;
import com.example.hotelmanagment.entity.Order;
import com.example.hotelmanagment.entity.Room;
import com.example.hotelmanagment.entity.User;
import com.example.hotelmanagment.repository.OrderRepository;
import com.example.hotelmanagment.repository.RoomRepository;
import com.example.hotelmanagment.repository.UserRepository;
import com.example.hotelmanagment.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderDtoUtil dtoUtil;
    private RoomRepository roomRepository;
    private UserRepository userRepository;

    @Override
    public void createOrder(OrderDto orderDto, long userId, long roomId) {
        List<Order> orders = orderRepository.findByRoomId(roomId);
        orders.reversed();

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));

        // checking orders is exist
        if(!orders.isEmpty()){
            Order order = orders.getFirst();
            // checking room isn't already booked
            if(order.getEndDate().isBefore(orderDto.getBeginDate())) {
                Order newOrder = dtoUtil.toEntity(orderDto);
                newOrder.setUser(user);
                newOrder.setRoom(room);
                orderRepository.save(newOrder);
            }
        } else {
            Order newOrder = dtoUtil.toEntity(orderDto);
            newOrder.setUser(user);
            newOrder.setRoom(room);
            orderRepository.save(newOrder);
        }

    }

    @Override
    public void updateOrder(OrderDto orderDto) {
        Order order = orderRepository.findById(orderDto.getId()).orElseThrow(() -> new RuntimeException("Order not found"));

        order.setEndDate(orderDto.getEndDate());
        order.setBeginDate(orderDto.getBeginDate());

        // setting room to the order
        Room room = roomRepository.findByOrderId(order.getId()).orElseThrow(() -> new RuntimeException("Room not found"));
        order.setRoom(room);

        // setting user to the order
        User user = userRepository.findByOrderId(order.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);

        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return dtoUtil.toDto(orders);
    }

    @Override
    public OrderDto getOrderById(long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));


        return dtoUtil.toDto(order);
    }

    @Override
    public OrderDto getOrderByRoomId(long roomId) {
        List<Order> order = orderRepository.findByRoomId(roomId);

        return dtoUtil.toDto(order.getFirst());
    }

    @Override
    public List<OrderDto> getOrderByUserId(long userId) {
        List<Order> order = orderRepository.findByUserId(userId);
        return dtoUtil.toDto(order);
    }
}
