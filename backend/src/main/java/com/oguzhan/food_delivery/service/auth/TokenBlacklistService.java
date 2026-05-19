package com.oguzhan.food_delivery.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {
    private final StringRedisTemplate stringRedisTemplate;
    private static final String BLACKLIST_PREFIX = "blacklist::";

    public void blacklistToken(String token, Duration expirationTime) {
        String key = BLACKLIST_PREFIX + token;
        stringRedisTemplate.opsForValue().set(key, "true", expirationTime);
    }

    public boolean isTokenBlacklisted(String token) {
        String key = BLACKLIST_PREFIX + token;
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

}
