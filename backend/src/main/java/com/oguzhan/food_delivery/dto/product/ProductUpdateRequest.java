package com.oguzhan.food_delivery.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductUpdateRequest(@NotBlank(message = "Ürün ismi boş bırakılamaz!") String name,
                                   String description,
                                   @NotNull(message = "Ürün fiyatı boş bırakılamaz!") @Positive BigDecimal price,
                                   String imageUrl,
                                   @NotNull(message = "Ürünün mevcut stok durumu boş bırakılamaz! ") Boolean isAvailable,
                                   @NotNull(message = "Kategori ID boş bırakılamaz!") Long categoryId) {
}
