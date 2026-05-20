package com.oguzhan.food_delivery.controller.restaurant;

import com.oguzhan.food_delivery.dto.restaurant.RestaurantListResponse;
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
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<RestaurantListResponse> findAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }


    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> findRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurant(restaurantId));
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
