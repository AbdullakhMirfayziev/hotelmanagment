package com.example.hotelmanagment.service;

import com.example.hotelmanagment.dto.ReviewDto;
import com.example.hotelmanagment.entity.Review;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReviewService {
    void addReview(ReviewDto reviewDto, long userId, long roomId);
    List<ReviewDto> getReviews();
    ReviewDto getReviewById(long id);
    void deleteReviewById(long id);
    void updateReview(ReviewDto reviewDto);
    List<ReviewDto> getByRoomId(long roomId);

}
