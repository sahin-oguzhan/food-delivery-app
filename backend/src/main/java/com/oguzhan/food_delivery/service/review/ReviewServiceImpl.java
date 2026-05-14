package com.oguzhan.food_delivery.service.review;

import com.oguzhan.food_delivery.dto.review.ReviewRequest;
import com.oguzhan.food_delivery.dto.review.ReviewResponse;
import com.oguzhan.food_delivery.entity.Review;
import com.oguzhan.food_delivery.entity.User;
import com.oguzhan.food_delivery.entity.order.Order;
import com.oguzhan.food_delivery.entity.order.OrderStatus;
import com.oguzhan.food_delivery.mapper.review.ReviewMapper;
import com.oguzhan.food_delivery.repository.ReviewRepository;
import com.oguzhan.food_delivery.repository.order.OrderRepository;
import com.oguzhan.food_delivery.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final CurrentUserService currentUserService;
    private final OrderRepository orderRepository;
    private final ReviewMapper reviewMapper;


    @Override
    @Transactional
    public ReviewResponse createReview(ReviewRequest reviewRequest) {
        User user = currentUserService.getCurrentUser();
        Order order =  orderRepository.findById(reviewRequest.orderId())
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı!"));

        if (!order.getCustomer().getId().equals(user.getId())) {
            throw new RuntimeException("Sadece kendi siparişlerinizi değerlendirebilirsiniz!");
        }

        if (order.getOrderStatus() != OrderStatus.DELIVERED) {
            throw new RuntimeException("Siparişi değerlendirebilmek için teslim edilmiş olması gerekir!");
        }

        if (reviewRepository.existsByOrderId(order.getId())) {
            throw new RuntimeException("Bu sipariş için zaten bir değerlendirme yaptınız!");
        }

        Review review = new Review();
        review.setCustomer(user);
        review.setRestaurant(order.getRestaurant());
        review.setOrder(order);
        review.setRating(reviewRequest.rating());
        review.setComment(reviewRequest.comment());
        review.setReviewDate(LocalDateTime.now());

        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    @Override
    public List<ReviewResponse> getRestaurantReviews(Long restaurantId) {
        return reviewRepository.findByRestaurantId(restaurantId).stream()
                .map(reviewMapper::toResponse)
                .toList();
    }
}
