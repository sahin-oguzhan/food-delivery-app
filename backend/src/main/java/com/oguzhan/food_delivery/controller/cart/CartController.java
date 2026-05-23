package com.oguzhan.food_delivery.controller.cart;

import com.oguzhan.food_delivery.dto.cart.AddToCartRequest;
import com.oguzhan.food_delivery.dto.cart.CartResponse;
import com.oguzhan.food_delivery.dto.cart.UpdateCartRequest;
import com.oguzhan.food_delivery.service.cart.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponse> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody AddToCartRequest addToCartRequest) {
        return ResponseEntity.ok(cartService.addToCart(addToCartRequest.productId(), addToCartRequest.quantity()));
    }

    @PutMapping("/update")
    public ResponseEntity<CartResponse> updateCart(@Valid @RequestBody UpdateCartRequest updateCartRequest) {
        return ResponseEntity.ok(cartService.updateCart(updateCartRequest.productId(), updateCartRequest.quantity()));
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<CartResponse> removeItemFromCart(@PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(cartItemId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<CartResponse> clearCart() {
        return ResponseEntity.ok(cartService.clearCart());
    }
}
