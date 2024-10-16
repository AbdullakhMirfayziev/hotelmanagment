package com.example.hotelmanagment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@Schema(description = "Hotel Data Transfer Object")
public class HotelDto {
    @Schema(description = "Unique identifier of the hotel", example = "1")
    private Long id;

    @Schema(description = "Name of the hotel", example = "Hilton")
    private String name;

    @Schema(description = "Address of the hotel", example = "Tashkent, Tashkent city")
    private String address;

    @Schema(description = "Phone number of the hotel", example = "+998998887766")
    private String phone;
}
