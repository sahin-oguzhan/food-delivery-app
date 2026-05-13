package com.oguzhan.food_delivery.dto.customer;

import java.util.List;

public record MenuCategoryResponse(Long id,
                                   String name,
                                   String description,
                                   List<MenuItemResponse> items) {
}
