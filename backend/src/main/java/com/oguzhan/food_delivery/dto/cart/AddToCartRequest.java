package com.oguzhan.food_delivery.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddToCartRequest(@NotNull Long productId,
                               @NotNull @Min(value = 1) Integer quantity) {
}
