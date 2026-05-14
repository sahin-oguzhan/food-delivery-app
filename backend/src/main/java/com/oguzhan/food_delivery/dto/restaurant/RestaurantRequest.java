package com.oguzhan.food_delivery.dto.restaurant;

import jakarta.validation.constraints.NotBlank;

public record RestaurantRequest(@NotBlank(message = "Restoran ismi boş bırakılamaz!") String name,
                                @NotBlank(message = "Restoran adresi boş bırakılamaz!") String address,
                                String description,
                                @NotBlank(message = "Restoran numarası boş bırakılamaz!") String phoneNumber) {
}
