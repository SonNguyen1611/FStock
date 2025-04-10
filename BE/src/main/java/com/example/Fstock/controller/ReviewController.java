package com.example.Fstock.controller;

import com.example.Fstock.dto.request.ReviewRequest;
import com.example.Fstock.dto.response.ApiResponse;
import com.example.Fstock.dto.response.ReviewResponse;
import com.example.Fstock.service.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse> createReview (@RequestBody ReviewRequest reviewRequest){
        return ResponseEntity.ok(ApiResponse.<ReviewResponse>builder()
                .message("Review created success")
                .data(reviewService.createReview(reviewRequest))
                .build());
    }
}
