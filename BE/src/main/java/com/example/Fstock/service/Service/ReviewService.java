package com.example.Fstock.service.Service;

import com.example.Fstock.dto.request.ReviewRequest;
import com.example.Fstock.dto.response.ReviewResponse;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest reviewRequest);
}
