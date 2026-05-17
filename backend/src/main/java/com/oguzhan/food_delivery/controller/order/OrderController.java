package com.oguzhan.food_delivery.controller.order;

import com.oguzhan.food_delivery.dto.order.OrderResponse;
import com.oguzhan.food_delivery.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrderHistory() {
        return ResponseEntity.ok(orderService.getOrderHistory());
    }
}
