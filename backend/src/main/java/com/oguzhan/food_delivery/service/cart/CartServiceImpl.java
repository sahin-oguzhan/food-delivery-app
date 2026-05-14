package com.oguzhan.food_delivery.service.cart;

import com.oguzhan.food_delivery.dto.cart.CartResponse;
import com.oguzhan.food_delivery.entity.Product;
import com.oguzhan.food_delivery.entity.User;
import com.oguzhan.food_delivery.entity.cart.Cart;
import com.oguzhan.food_delivery.entity.cart.CartItem;
import com.oguzhan.food_delivery.mapper.cart.CartMapper;
import com.oguzhan.food_delivery.repository.ProductRepository;
import com.oguzhan.food_delivery.repository.UserRepository;
import com.oguzhan.food_delivery.repository.cart.CartRepository;
import com.oguzhan.food_delivery.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CurrentUserService currentUserService;
    private final ProductRepository productRepository;


    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart() {
        User user = currentUserService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> createNewCart(user));

        return cartMapper.toCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse addToCart(Long productId, Integer quantity) {
        User user = currentUserService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> createNewCart(user));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı!"));

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setSubTotal(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setSubTotal(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            cart.addItem(newItem);
        }
        updateCartTotal(cart);
        cartRepository.save(cart);
        return cartMapper.toCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse removeItemFromCart(Long cartItemId) {
        User user = currentUserService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Sepet bulunamadı!"));

        CartItem itemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sepette böyle bir ürün bulunamadı!"));

        cart.getCartItems().remove(itemToRemove);
        updateCartTotal(cart);

        return cartMapper.toCartResponse(cartRepository.save(cart));
    }

    private Cart createNewCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(BigDecimal.ZERO);
        return cartRepository.save(cart);
    }

    private void updateCartTotal(Cart cart) {
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(CartItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);
    }


}
