package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.ReviewDto;
import com.example.hotelmanagment.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reviews")
@AllArgsConstructor
public class ReviewController {
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<?> getAllReviews(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        if (page < 1 || size <= 0) {
            return ResponseEntity.badRequest().body("Invalid page or size parameters");
        }

        Page<ReviewDto> reviews = reviewService.getReviewsWithPageable(size, page - 1);

        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Map<String , Object> response = new HashMap<>();
        response.put("reviews", reviews.getContent());
        response.put("currentPage", reviews.getNumber());
        response.put("totalItems", reviews.getTotalElements());
        response.put("totalPages", reviews.getTotalPages());

        return ResponseEntity.ok(response);
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
