package com.oguzhan.food_delivery.dto.restaurant;

public record RestaurantResponseDto(Long id,
                                    String name,
                                    String description,
                                    String address,
                                    String phoneNumber,
                                    String ownerFirstName,
                                    String ownerLastName,
                                    String ownerEmail) {
}
