package com.oguzhan.food_delivery.controller;

import com.oguzhan.food_delivery.dto.restaurant.RestaurantRequest;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantResponse;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantUpdateRequest;
import com.oguzhan.food_delivery.service.restaurant.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<RestaurantResponse> findRestaurant() {
        return ResponseEntity.ok(restaurantService.getRestaurant());
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@Valid @RequestBody RestaurantRequest restaurantRequest) {
        RestaurantResponse createdRestaurant = restaurantService.createRestaurant(restaurantRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurant);
    }

    @PutMapping
    public ResponseEntity<RestaurantResponse> updateRestaurant(@Valid @RequestBody RestaurantUpdateRequest restaurantUpdateRequest) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(restaurantUpdateRequest));
    }
}
