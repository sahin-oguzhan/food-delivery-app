package com.oguzhan.food_delivery.service.order;

import com.oguzhan.food_delivery.dto.order.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder();
    List<OrderResponse> getOrderHistory();
}
