package com.oguzhan.food_delivery.service.restaurant;

import com.oguzhan.food_delivery.dto.restaurant.RestaurantRequestDto;
import com.oguzhan.food_delivery.entity.Restaurant;

public interface RestaurantService {
    public Restaurant createRestaurant(RestaurantRequestDto restaurantRequestDto);
}
