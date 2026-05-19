package com.oguzhan.food_delivery.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RateLimitingInterceptor implements HandlerInterceptor {
    private final StringRedisTemplate redisTemplate;
    private static final String RATE_LIMIT_PREFIX = "rateLimit::";
    private static final int MAX_REQUESTS_PER_MINUTE = 20;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = request.getRemoteAddr();
        String key = RATE_LIMIT_PREFIX + ipAddress;

        Long currentRequests = redisTemplate.opsForValue().increment(key);

        if (currentRequests != null && currentRequests == 1) {
            redisTemplate.expire(key, 1 , TimeUnit.MINUTES);
        }

        if (currentRequests != null && currentRequests > MAX_REQUESTS_PER_MINUTE) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write("Çok sayıda istekte bulundunuz. 1 dakika sonra tekrar deneyin!");
            return false;
        }
        return true;
    }
}
