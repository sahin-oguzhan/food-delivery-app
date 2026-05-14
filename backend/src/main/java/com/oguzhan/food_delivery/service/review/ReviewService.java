package com.oguzhan.food_delivery.service.review;

import com.oguzhan.food_delivery.dto.review.ReviewRequest;
import com.oguzhan.food_delivery.dto.review.ReviewResponse;
import com.oguzhan.food_delivery.entity.Review;

import java.util.List;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest reviewRequest);
    List<ReviewResponse> getRestaurantReviews(Long restaurantId);
}
