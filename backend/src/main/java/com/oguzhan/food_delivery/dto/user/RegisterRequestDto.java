package com.oguzhan.food_delivery.dto.user;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto(@NotBlank(message = "İsim boş bırakılamaz!") String firstName,
                                 @NotBlank(message = "Soyisim boş bırakılamaz!") String lastName,
                                 @NotBlank(message = "Kullanıcı adı boş bırakılamaz!") String username,
                                 @NotBlank(message = "Eposta boş bırakılamaz!") String email,
                                 @NotBlank(message = "Şifre boş bırakılamaz!") String password,
                                 Boolean isOwner) {
}
