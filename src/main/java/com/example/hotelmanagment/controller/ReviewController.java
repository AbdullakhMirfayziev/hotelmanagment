package com.example.hotelmanagment.controller;

import com.example.hotelmanagment.dto.ReviewDto;
import com.example.hotelmanagment.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reviews")
@AllArgsConstructor
@Tag(name = "Review", description = "Review management APIs")
public class ReviewController {
    private ReviewService reviewService;

    @Operation(summary = "Get all reviews", description = "Retrieve a paginated list of all reviews")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reviews",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "204", description = "No reviews found"),
            @ApiResponse(responseCode = "400", description = "Invalid page or size parameters")
    })
    @GetMapping
    public ResponseEntity<?> getAllReviews(@Parameter(description = "Page number", example = "1") @RequestParam(defaultValue = "1") int page,
                                           @Parameter(description = "Number of items per page", example = "5") @RequestParam(defaultValue = "5") int size) {
        if (page < 1 || size <= 0) {
            return ResponseEntity.badRequest().body("Invalid page or size parameters");
        }

        Page<ReviewDto> reviews = reviewService.getReviewsWithPageable(size, page - 1);

        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviews.getContent());
        response.put("currentPage", reviews.getNumber());
        response.put("totalItems", reviews.getTotalElements());
        response.put("totalPages", reviews.getTotalPages());

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Get a review by ID", description = "Retrieve a review by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the review",
                    content = @Content(schema = @Schema(implementation = ReviewDto.class))),
            @ApiResponse(responseCode = "204", description = "Review not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@Parameter(description = "Review ID", example = "1") @PathVariable("id") int id) {
        ReviewDto reviewDto = reviewService.getReviewById(id);

        if (reviewDto == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reviewDto);
    }

    @Operation(summary = "Add a new review", description = "Create a new review for a user and room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/users/{userId}/rooms/{roomId}")
    public ResponseEntity<?> addReview(@Parameter(description = "User ID", example = "1") @PathVariable("userId") int userId,
                                       @Parameter(description = "Room ID", example = "1") @PathVariable("roomId") int roomId,
                                       @Parameter(description = "Review details", required = true) @RequestBody ReviewDto reviewDto) {
        reviewService.addReview(reviewDto, userId, roomId);

        return ResponseEntity.status(HttpStatus.CREATED).body("review created successfully");
    }

    @Operation(summary = "Update a review", description = "Update an existing review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @PutMapping
    public ResponseEntity<?> updateReview(@Parameter(description = "Updated review details", required = true) @RequestBody ReviewDto reviewDto) {
        reviewService.updateReview(reviewDto);

        return ResponseEntity.ok("review updated successfully");
    }

    @Operation(summary = "Delete a review", description = "Delete a review by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@Parameter(description = "Review ID", example = "1") @PathVariable("id") int id) {
        reviewService.deleteReviewById(id);

        return ResponseEntity.ok("review deleted successfully");
    }

}
