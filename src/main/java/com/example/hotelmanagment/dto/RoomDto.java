package com.example.hotelmanagment.dto;

import com.example.hotelmanagment.enums.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@Schema(description = "Room Data Transfer Object")
public class RoomDto {

    @Schema(description = "Unique identifier of the room", example = "1")
    private long id;

    @Schema(description = "Room number", example = "101")
    private int number;

    @Schema(description = "Price per night", example = "100.50")
    private double price;

    @Schema(description = "Room category", example = "STANDARD")
    private Category category;

    @Schema(description = "ID of the hotel this room belongs to", example = "1")
    private long hotelId;

    @Schema(description = "avarage rating of the room", example = "8.6")
    private double avrgRating;
}