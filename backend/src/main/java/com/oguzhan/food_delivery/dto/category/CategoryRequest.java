package com.oguzhan.food_delivery.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(@NotBlank(message = "Kategori ismi boş bırakılamaz!") String name,
                              String description) {
}
