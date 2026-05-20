package com.oguzhan.food_delivery.repository;

import com.oguzhan.food_delivery.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByRestaurantId(Long restaurantId);
    Optional<Category> findByName(String categoryName);
}
