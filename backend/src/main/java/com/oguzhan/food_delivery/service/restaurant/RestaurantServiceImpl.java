package com.oguzhan.food_delivery.service.restaurant;

import com.oguzhan.food_delivery.dto.restaurant.RestaurantRequestDto;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantResponseDto;
import com.oguzhan.food_delivery.entity.Restaurant;
import com.oguzhan.food_delivery.entity.User;
import com.oguzhan.food_delivery.mapper.RestaurantMapper;
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
    @Transactional
    public RestaurantResponseDto createRestaurant(RestaurantRequestDto restaurantRequestDto) {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (restaurantRepository.findByOwnerId(currentUser.getId()).isPresent()) {
            throw new RuntimeException("Zaten bir restoranınız bulunuyor!");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequestDto.name());
        restaurant.setAddress(restaurantRequestDto.address());
        restaurant.setPhoneNumber(restaurantRequestDto.phoneNumber());
        restaurant.setDescription(restaurantRequestDto.description());
        restaurant.setOwner(currentUser);

        return restaurantMapper.toResponse(restaurantRepository.save(restaurant));
    }
}
