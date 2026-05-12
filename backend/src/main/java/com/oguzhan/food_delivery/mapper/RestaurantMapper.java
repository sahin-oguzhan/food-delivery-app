package com.oguzhan.food_delivery.mapper;

import com.oguzhan.food_delivery.dto.restaurant.RestaurantRequestDto;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantResponseDto;
import com.oguzhan.food_delivery.entity.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public RestaurantResponseDto toResponse(Restaurant restaurant) {
        return new RestaurantResponseDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getAddress(),
                restaurant.getPhoneNumber(),
                restaurant.getOwner().getFirstName(),
                restaurant.getOwner().getLastName(),
                restaurant.getOwner().getEmail()
        );
    }
}
