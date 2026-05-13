package com.oguzhan.food_delivery.service.cart;

import com.oguzhan.food_delivery.dto.cart.CartResponse;
import com.oguzhan.food_delivery.entity.User;
import com.oguzhan.food_delivery.entity.cart.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface CartService {
    CartResponse addToCart(Long productId, Integer quantity);
}
