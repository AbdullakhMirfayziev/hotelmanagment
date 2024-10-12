package com.example.hotelmanagment.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class HotelDto {
    private long id;
    private String name;
    private String address;
    private String phone;
}
