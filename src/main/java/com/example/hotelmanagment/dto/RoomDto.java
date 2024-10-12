package com.example.hotelmanagment.dto;

import com.example.hotelmanagment.enums.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoomDto {
    private long id;
    private int number;
    private double price;
    private Category category;
    private long hotelId;
}
