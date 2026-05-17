package com.oguzhan.food_delivery.controller.stripe;

import com.oguzhan.food_delivery.dto.stripe.PaymentRequest;
import com.oguzhan.food_delivery.dto.stripe.PaymentResponse;
import com.oguzhan.food_delivery.service.stripe.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPaymentIntent(@RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(paymentService.createPaymentIntent(paymentRequest));
    }
}
