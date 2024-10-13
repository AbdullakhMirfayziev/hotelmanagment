package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.ReviewDto;
import com.example.hotelmanagment.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@AllArgsConstructor
public class ReviewController {
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<?> getAllReviews() {
        List<ReviewDto> reviewDtoList = reviewService.getReviews();

        if (reviewDtoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reviewDtoList);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable("id") int id) {
        ReviewDto reviewDto = reviewService.getReviewById(id);

        if (reviewDto == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reviewDto);
    }

    @PostMapping("/users/{userId}/rooms/{roomId}")
    public ResponseEntity<?> addReview(@PathVariable("userId") int userId, @PathVariable("roomId") int roomId, @RequestBody ReviewDto reviewDto) {
        reviewService.addReview(reviewDto, userId, roomId);

        return ResponseEntity.status(HttpStatus.CREATED).body("review created successfully");
    }

    @PutMapping
    public ResponseEntity<?> updateReview(@RequestBody ReviewDto reviewDto) {
        reviewService.updateReview(reviewDto);

        return ResponseEntity.ok("review updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable("id") int id) {
        reviewService.deleteReviewById(id);

        return ResponseEntity.ok("review deleted successfully");
    }

}
