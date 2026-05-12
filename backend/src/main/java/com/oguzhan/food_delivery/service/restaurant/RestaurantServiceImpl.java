package com.oguzhan.food_delivery.service.restaurant;

import com.oguzhan.food_delivery.dto.restaurant.RestaurantRequest;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantResponse;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantUpdateRequest;
import com.oguzhan.food_delivery.entity.Restaurant;
import com.oguzhan.food_delivery.entity.User;
import com.oguzhan.food_delivery.mapper.restaurant.RestaurantMapper;
import com.oguzhan.food_delivery.repository.RestaurantRepository;
import com.oguzhan.food_delivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final RestaurantMapper restaurantMapper;

    @Override
    public RestaurantResponse getRestaurant() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Restaurant restaurant = restaurantRepository.findByOwnerId(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Henüz bir restoranınız bulunmamaktadır."));

        return restaurantMapper.toResponse(restaurant);
    }

    @Override
    @Transactional
    public RestaurantResponse createRestaurant(RestaurantRequest restaurantRequest) {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        if (restaurantRepository.findByOwnerId(currentUser.getId()).isPresent()) {
            throw new RuntimeException("Zaten bir restoranınız bulunuyor!");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.name());
        restaurant.setAddress(restaurantRequest.address());
        restaurant.setPhoneNumber(restaurantRequest.phoneNumber());
        restaurant.setDescription(restaurantRequest.description());
        restaurant.setOwner(currentUser);

        return restaurantMapper.toResponse(restaurantRepository.save(restaurant));
    }

    @Override
    @Transactional
    public RestaurantResponse updateRestaurant(RestaurantUpdateRequest restaurantUpdateRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        Restaurant restaurant = restaurantRepository.findByOwnerId(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Güncellenecek bir restoran bulunamadı!"));

        restaurant.setName(restaurantUpdateRequest.name());
        restaurant.setAddress(restaurantUpdateRequest.address());
        restaurant.setPhoneNumber(restaurantUpdateRequest.phoneNumber());
        restaurant.setDescription(restaurantUpdateRequest.description());

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        return restaurantMapper.toResponse(updatedRestaurant);
    }
}
