package com.oguzhan.food_delivery.dto.customer;

import java.math.BigDecimal;

public record MenuItemResponse(Long id,
                               String name,
                               String description,
                               BigDecimal price,
                               String imageUrl) {
}
