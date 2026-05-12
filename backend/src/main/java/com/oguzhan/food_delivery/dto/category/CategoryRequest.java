package com.oguzhan.food_delivery.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(@NotBlank String name,
                              String description) {
}
