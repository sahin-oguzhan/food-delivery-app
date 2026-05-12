package com.oguzhan.food_delivery.controller;

import com.oguzhan.food_delivery.dto.restaurant.RestaurantRequestDto;
import com.oguzhan.food_delivery.entity.Restaurant;
import com.oguzhan.food_delivery.service.restaurant.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/owner/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody RestaurantRequestDto restaurantRequestDto) {
        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurantRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurant);
    }
}
