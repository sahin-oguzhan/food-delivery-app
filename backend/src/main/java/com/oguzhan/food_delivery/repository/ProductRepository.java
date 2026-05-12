package com.oguzhan.food_delivery.repository;

import com.oguzhan.food_delivery.dto.product.ProductResponse;
import com.oguzhan.food_delivery.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<ProductResponse> findByRestaurantId(Long restaurantId);
    List<ProductResponse> findByRestaurantIdAndIsAvailableTrue(Long restaurantId);
}
