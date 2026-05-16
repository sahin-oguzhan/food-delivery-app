package com.oguzhan.food_delivery.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.jspecify.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;


public record ProductRequest(@NotBlank(message = "Ürün ismi boş bırakılamaz!") String name,
                             @NotNull(message = "Ürün fiyatı boş bırakılamaz!") @Positive BigDecimal price,
                             String description,
                             @NotNull(message = "Kategori ID boş bırakılamaz!") Long categoryId) {
}
