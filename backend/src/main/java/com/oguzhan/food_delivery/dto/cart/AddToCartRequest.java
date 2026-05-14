package com.oguzhan.food_delivery.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddToCartRequest(@NotNull(message = "Ürün ID boş bırakılamaz!") Long productId,
                               @NotNull(message = "Miktar boş bırakılamaz") @Min(value = 1) Integer quantity) {
}
