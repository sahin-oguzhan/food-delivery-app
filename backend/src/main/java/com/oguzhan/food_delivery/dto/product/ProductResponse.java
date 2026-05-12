package com.oguzhan.food_delivery.dto.product;

import java.math.BigDecimal;

public record ProductResponse(Long id,
                              String name,
                              String description,
                              BigDecimal price,
                              String imageUrl,
                              boolean isAvailable,
                              String restaurantName,
                              Long restaurantId,
                              String categoryName,
                              Long categoryId

) {
}
