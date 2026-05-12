package com.oguzhan.food_delivery.dto.category;

public record CategoryResponse(Long id,
                               String name,
                               String description,
                               Long restaurantId) {
}
