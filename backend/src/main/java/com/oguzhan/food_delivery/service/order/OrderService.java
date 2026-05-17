package com.oguzhan.food_delivery.service.order;

import com.oguzhan.food_delivery.dto.order.OrderResponse;
import com.oguzhan.food_delivery.entity.order.OrderStatus;

import java.util.List;

public interface OrderService {
    void createNewPaidOrder(Long cartId, String stripePaymentIntentId);
    List<OrderResponse> getOrderHistory();
    List<OrderResponse> getRestaurantOrders();
    OrderResponse updateOrderStatus(Long orderId, OrderStatus orderStatus);

}
