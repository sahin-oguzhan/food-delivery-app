package com.oguzhan.food_delivery.service.restaurant;

import com.oguzhan.food_delivery.dto.customer.RestaurantMenuResponse;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantRequest;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantResponse;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantUpdateRequest;

import java.util.List;

public interface RestaurantService {
    public RestaurantResponse getRestaurant();
    public List<RestaurantResponse> getAllRestaurants();
    public RestaurantMenuResponse getRestaurantMenu(Long restaurantId);
    public RestaurantResponse createRestaurant(RestaurantRequest restaurantRequest);
    public RestaurantResponse updateRestaurant(RestaurantUpdateRequest restaurantUpdateRequest);
}
