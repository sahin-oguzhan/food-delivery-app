package com.oguzhan.food_delivery.repository;

import com.oguzhan.food_delivery.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByRestaurantId(Long restaurantId);
    boolean existsByOrderId(Long orderId);
}
