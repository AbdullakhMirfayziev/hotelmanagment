package com.example.hotelmanagment.dto;

import com.example.hotelmanagment.entity.Review;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewDtoUtil {

    public ReviewDto toDto(Review review) {
        ReviewDto reviewDto = ReviewDto.builder()
                .id(review.getId())
                .comment(review.getComment())
                .rating(review.getRating())
                .build();

        return reviewDto;
    }

    public Review toEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());

        return review;
    }

    public List<ReviewDto> toDto(List<Review> reviewList) {
        return reviewList.stream().map(this::toDto).toList();
    }

    public List<Review> toEntity(List<ReviewDto> reviewDtoList) {
        return reviewDtoList.stream().map(this::toEntity).toList();
    }
}
