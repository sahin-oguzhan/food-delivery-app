package com.oguzhan.food_delivery.dto.websocket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record OrderNotification(Long orderId,
                                String message,
                                String orderStatus,
                                String timestamp) {
    public OrderNotification(Long orderId, String message, String orderStatus) {
        this(orderId, message, orderStatus, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
    }
}
