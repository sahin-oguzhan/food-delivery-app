package com.oguzhan.food_delivery.repository.order;

import com.oguzhan.food_delivery.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerIdOrderByOrderDateDesc (Long customerId);
    List<Order> findByRestaurantOwnerId (Long ownerId);
}
