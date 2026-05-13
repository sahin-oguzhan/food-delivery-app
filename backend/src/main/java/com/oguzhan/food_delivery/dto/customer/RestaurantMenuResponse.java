package com.oguzhan.food_delivery.dto.customer;

import java.util.List;

public record RestaurantMenuResponse(Long restaurantId,
                                     String restaurantName,
                                     List<MenuCategoryResponse> categories) {
}
