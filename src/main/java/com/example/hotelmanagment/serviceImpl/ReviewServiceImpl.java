package com.example.hotelmanagment.serviceImpl;

import com.example.hotelmanagment.dto.ReviewDto;
import com.example.hotelmanagment.dto.ReviewDtoUtil;
import com.example.hotelmanagment.entity.Review;
import com.example.hotelmanagment.entity.Room;
import com.example.hotelmanagment.entity.User;
import com.example.hotelmanagment.repository.ReviewRepository;
import com.example.hotelmanagment.repository.RoomRepository;
import com.example.hotelmanagment.repository.UserRepository;
import com.example.hotelmanagment.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private ReviewDtoUtil dtoUtil;

    @Override
    public void addReview(ReviewDto reviewDto, long userId, long roomId) {
        Review review = dtoUtil.toEntity(reviewDto);

        // setting user to the review
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        review.setUser(user);

        // setting room to the review
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room Not Found"));
        review.setRoom(room);

        reviewRepository.save(review);
    }

    @Override
    public List<ReviewDto> getReviews() {
        List<Review> reviews = reviewRepository.findAll();

        return dtoUtil.toDto(reviews);
    }

    @Override
    public ReviewDto getReviewById(long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));

        return dtoUtil.toDto(review);
    }

    @Override
    public void deleteReviewById(long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public void updateReview(ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewDto.getId()).orElseThrow(() -> new RuntimeException("User Not Found"));

        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());

        // setting user to the review
        User user = userRepository.findByReviewId(review.getId()).orElseThrow(() -> new RuntimeException("User Not Found"));
        review.setUser(user);

        // setting room to the review
        Room room = roomRepository.findByReviewId(review.getId()).orElseThrow(() -> new RuntimeException("Room Not Found"));
        review.setRoom(room);

        reviewRepository.save(review);
    }

    @Override
    public List<ReviewDto> getByRoomId(long roomId) {
        List<Review> reviews = reviewRepository.findByRoomId(roomId);

        return dtoUtil.toDto(reviews);
    }
}
