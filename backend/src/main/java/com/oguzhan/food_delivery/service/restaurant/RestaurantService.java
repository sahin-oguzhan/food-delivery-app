package com.oguzhan.food_delivery.service.restaurant;

import com.oguzhan.food_delivery.dto.restaurant.RestaurantRequestDto;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantResponseDto;

public interface RestaurantService {
    public RestaurantResponseDto createRestaurant(RestaurantRequestDto restaurantRequestDto);
}
