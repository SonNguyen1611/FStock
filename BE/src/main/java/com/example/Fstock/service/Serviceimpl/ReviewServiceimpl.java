package com.example.Fstock.service.Serviceimpl;

import com.example.Fstock.dto.request.ReviewRequest;
import com.example.Fstock.dto.response.ReviewResponse;
import com.example.Fstock.entity.Product;
import com.example.Fstock.entity.Review;
import com.example.Fstock.entity.User;
import com.example.Fstock.exception.InternalServerErrorException;
import com.example.Fstock.exception.NotFoundException;
import com.example.Fstock.responsitory.ProductRepository;
import com.example.Fstock.responsitory.ReviewRepository;
import com.example.Fstock.responsitory.UserRepository;
import com.example.Fstock.service.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class ReviewServiceimpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ReviewResponse createReview(ReviewRequest reviewRequest) {
        User user = userRepository.findByEmail(reviewRequest.getEmail()).orElseThrow(() -> new NotFoundException("Your account have problem"));

        Product product = productRepository.findByProductId(reviewRequest.getProductId());
        if (product == null){
            throw new NotFoundException("Product not exist in system");
        }
        Review review = new Review();
        review.setContent(reviewRequest.getContent());
        review.setRating(reviewRequest.getRating());
        review.setUser(user);
        review.setProduct(product);
        review.setCreatedAt(new Date());
        try {
            reviewRepository.save(review);
            return ReviewResponse.builder()
                    .id(review.getId())
                    .content(review.getContent())
                    .rating(review.getRating())
                    .createdAt(review.getCreatedAt())
                    .build();
        } catch (Exception e) {
            throw new InternalServerErrorException("create review failed");
        }
    }
}
