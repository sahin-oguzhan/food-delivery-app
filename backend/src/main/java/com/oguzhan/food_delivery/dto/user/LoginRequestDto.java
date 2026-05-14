package com.oguzhan.food_delivery.dto.user;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(@NotBlank(message = "Eposta boş bırakılamaz!") String email,
                              @NotBlank(message = "Şifre boş bırakılamaz!") String password) {
}
