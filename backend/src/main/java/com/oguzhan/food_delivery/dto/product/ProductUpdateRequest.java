package com.oguzhan.food_delivery.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductUpdateRequest(@NotBlank String name,
                                   String description,
                                   @NotNull @Positive BigDecimal price,
                                   String imageUrl,
                                   @NotNull Boolean isAvailable,
                                   @NotNull Long categoryId) {
}
