package com.oguzhan.food_delivery.mapper.restaurant;

import com.oguzhan.food_delivery.dto.restaurant.RestaurantResponse;
import com.oguzhan.food_delivery.entity.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public RestaurantResponse toResponse(Restaurant restaurant) {
        if (restaurant == null) {
            return null;
        }

        return new RestaurantResponse(
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
