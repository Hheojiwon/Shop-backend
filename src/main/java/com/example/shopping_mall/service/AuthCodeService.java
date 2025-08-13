package com.example.shopping_mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthCodeService {
    private final RedisTemplate<String, String> redisTemplate;

    // 인증번호 저장 (5분 TTL)
    public void saveAuthCode(String email, String code) {
        redisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES);
    }

    // 인증번호 검증
    public boolean verifyAuthCode(String email, String code) {
        String savedCode = redisTemplate.opsForValue().get(email);
        return code.equals(savedCode);
    }
}

