package com.oguzhan.food_delivery.dto.restaurant;

import jakarta.validation.constraints.NotBlank;

public record RestaurantUpdateRequest(@NotBlank String name,
                                      @NotBlank String address,
                                      @NotBlank String phoneNumber,
                                      String description) {
}
