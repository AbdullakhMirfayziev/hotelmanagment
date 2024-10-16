package com.example.hotelmanagment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Review Data Transfer Object")
public class ReviewDto {
    @Schema(description = "Unique identifier of the review", example = "1")
    private long id;

    @Schema(description = "Comment of the payment for room", example = "This room is worth for its price")
    private String comment;

    @Schema(description = "Rating of the payment for room", example = "10")
    private int rating;
}
