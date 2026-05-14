package com.oguzhan.food_delivery.security;

import com.oguzhan.food_delivery.entity.Restaurant;
import com.oguzhan.food_delivery.entity.User;
import com.oguzhan.food_delivery.entity.cart.Cart;
import com.oguzhan.food_delivery.repository.RestaurantRepository;
import com.oguzhan.food_delivery.repository.UserRepository;
import com.oguzhan.food_delivery.repository.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final CartRepository cartRepository;

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Oturum açılmış kullanıcı bulunamadı!"));
    }

    public Restaurant getCurrentUserRestaurant() {
        User user = getCurrentUser();
        return restaurantRepository.findByOwnerId(user.getId())
                .orElseThrow(() -> new RuntimeException("Restoran bulunamadı!"));
    }

    public Cart getCurrentUserCart() {
        User user = getCurrentUser();
        return cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Sepet bulunamadı!"));
    }
}
