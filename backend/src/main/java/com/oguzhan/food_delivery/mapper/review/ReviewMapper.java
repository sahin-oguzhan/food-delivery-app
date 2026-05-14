package com.oguzhan.food_delivery.mapper.review;

import com.oguzhan.food_delivery.dto.review.ReviewResponse;
import com.oguzhan.food_delivery.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewResponse toResponse(Review review) {
        if (review == null) {
            return null;
        }

        return new ReviewResponse(
                review.getId(),
                review.getCustomer().getFirstName(),
                review.getRating(),
                review.getComment(),
                review.getReviewDate()
        );
    }
}
