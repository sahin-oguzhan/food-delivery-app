package com.oguzhan.food_delivery.dto.restaurant;

import jakarta.validation.constraints.NotBlank;

public record RestaurantRequest(@NotBlank String name,
                                @NotBlank String address,
                                String description,
                                @NotBlank String phoneNumber) {
}
