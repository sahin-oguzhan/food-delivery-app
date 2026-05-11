package com.oguzhan.food_delivery.dto.user;

public record RegisterRequestDto(String firstName,
                                 String lastName,
                                 String username,
                                 String email,
                                 String password,
                                 Boolean isOwner) {
}
