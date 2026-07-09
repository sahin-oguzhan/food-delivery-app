package com.oguzhan.food_delivery.dto.user;

import java.util.List;

public record UserResponseDTO(Long id,
                              String name,
                              String email,
                              List<String> roles,
                              Long restaurantId) {
}
