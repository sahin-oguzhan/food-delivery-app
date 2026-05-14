package com.oguzhan.food_delivery.service.order;

import com.oguzhan.food_delivery.dto.order.OrderResponse;
import com.oguzhan.food_delivery.entity.Restaurant;
import com.oguzhan.food_delivery.entity.User;
import com.oguzhan.food_delivery.entity.cart.Cart;
import com.oguzhan.food_delivery.entity.cart.CartItem;
import com.oguzhan.food_delivery.entity.order.Order;
import com.oguzhan.food_delivery.entity.order.OrderItem;
import com.oguzhan.food_delivery.entity.order.OrderStatus;
import com.oguzhan.food_delivery.mapper.order.OrderMapper;
import com.oguzhan.food_delivery.repository.cart.CartRepository;
import com.oguzhan.food_delivery.repository.order.OrderRepository;
import com.oguzhan.food_delivery.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CurrentUserService currentUserService;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse createOrder() {
        User user =  currentUserService.getCurrentUser();
        Cart cart = currentUserService.getCurrentUserCart();

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Sepetiniz boş, sipariş verilemez!");
        }

        Order order = new Order();
        order.setCustomer(user);

        Restaurant restaurant = cart.getCartItems().getFirst().getProduct().getRestaurant();
        order.setRestaurant(restaurant);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(cart.getTotalPrice());

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtOrder(cartItem.getProduct().getPrice());

            order.addOrderItem(orderItem);
        }

        Order savedOrder = orderRepository.save(order);

        cart.getCartItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);

        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getOrderHistory() {
        User user = currentUserService.getCurrentUser();
        List<Order> orders = orderRepository.findByCustomerIdOrderByOrderDateDesc(user.getId());

        return orders.stream()
                .map(orderMapper::toResponse)
                .toList();
    }

}
