package com.oguzhan.food_delivery.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(@NotBlank String name,
                             @NotNull BigDecimal price,
                             String description,
                             String imageUrl,
                             @NotNull Long categoryId) {
}
