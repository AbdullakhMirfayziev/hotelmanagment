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
    private LocalDate beginDate;
    private LocalDate endDate;
    private double price;
    private long hotelId;
    private long roomId;
}
