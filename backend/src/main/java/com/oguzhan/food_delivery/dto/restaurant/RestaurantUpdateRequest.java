package com.oguzhan.food_delivery.dto.restaurant;

import jakarta.validation.constraints.NotBlank;

public record RestaurantUpdateRequest(@NotBlank(message = "Restoran ismi boş bırakılamaz!") String name,
                                      @NotBlank(message = "Restoran adresi boş bırakılamaz!") String address,
                                      @NotBlank(message = "Restoran numarası boş bırakılamaz!") String phoneNumber,
                                      String description) {
}
