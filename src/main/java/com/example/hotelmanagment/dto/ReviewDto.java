package com.example.hotelmanagment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewDto {
    private long id;
    private String comment;
    private int rating;
}
