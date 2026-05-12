package com.oguzhan.food_delivery.service.restaurant;

import com.oguzhan.food_delivery.dto.restaurant.RestaurantRequest;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantResponse;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantUpdateRequest;

public interface RestaurantService {
    public RestaurantResponse getRestaurant();
    public RestaurantResponse createRestaurant(RestaurantRequest restaurantRequest);
    public RestaurantResponse updateRestaurant(RestaurantUpdateRequest restaurantUpdateRequest);
}
