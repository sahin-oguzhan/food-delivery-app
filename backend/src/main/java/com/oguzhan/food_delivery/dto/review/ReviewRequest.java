package com.oguzhan.food_delivery.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewRequest(@NotNull(message = "Sipariş ID boş bırakılamaz") Long orderId,
                            @NotNull(message = "Puan boş bırakılamaz!")
                            @Min(value = 1, message = "Puan en az 1 olabilir!")
                            @Max(value = 5, message = "Puan en fazla 5 olabilir!") Integer rating,
                            @Size(max = 500, message = "Yorumunu< 500 karakterden uzun olamaz!") String comment) {
}
