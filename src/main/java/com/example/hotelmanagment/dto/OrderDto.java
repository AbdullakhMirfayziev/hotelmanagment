package com.example.hotelmanagment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class OrderDto {
    private long id;
    private LocalDate orderDate;
    private int days;
    private double price;
    private long hotelId;
    private long roomId;
}
