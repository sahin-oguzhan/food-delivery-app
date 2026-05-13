package com.oguzhan.food_delivery.controller.customer;

import com.oguzhan.food_delivery.dto.customer.RestaurantMenuResponse;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantResponse;
import com.oguzhan.food_delivery.service.restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer/restaurants")
@RequiredArgsConstructor
public class CustomerRestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<RestaurantMenuResponse> getRestaurantMenu(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantMenu(restaurantId));
    }
}
