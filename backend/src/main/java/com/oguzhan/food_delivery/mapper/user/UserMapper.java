package com.oguzhan.food_delivery.mapper.user;

import com.oguzhan.food_delivery.dto.user.UserResponseDTO;
import com.oguzhan.food_delivery.entity.Role;
import com.oguzhan.food_delivery.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public UserResponseDTO toResponse(User user) {
        if (user == null) {
            return null;
        }

        List<String> roles = user.getRoles().stream()
                .map(Role::getAuthority)
                .toList();

        Long restaurantId = null;

        if (roles.contains("ROLE_OWNER") && user.getRestaurant() != null) {
            restaurantId = user.getRestaurant().getId();
        }

        return new UserResponseDTO(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                user.getEmail(),
                roles,
                restaurantId
        );
    }
}
