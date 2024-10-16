package com.example.hotelmanagment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Schema(description = "Order Data Transfer Object")
public class OrderDto {
    @Schema(description = "Unique identifier of the order", example = "1")
    private long id;

    @Schema(description = "Start date of the order", example = "2023-07-01")
    private LocalDate beginDate;

    @Schema(description = "End date of the order", example = "2023-07-07")
    private LocalDate endDate;

    @Schema(description = "ID of the room associated with this order", example = "1")
    private long roomId;
}
