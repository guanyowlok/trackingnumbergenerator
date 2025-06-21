package com.tracking.trackinggenerator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.tracking.trackinggenerator.exception.TrackingGenerationException;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackingNumberService {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${tracking.prefix}")
    private String prefix;

    @Value("${server.region}")
    private String region;

    @PostConstruct
public void checkRedisConnection() {
    try {
        String pong = redisTemplate.getConnectionFactory().getConnection().ping();
        System.out.println("✅ Redis connection OK: " + pong);
    } catch (Exception e) {
        System.err.println("❌ Redis connection failed: " + e.getMessage());
    }
}

    public String generateTrackingNumber(String originCountryId, String destinationCountryId) {
        String key = String.format("tracking:counter:%s", region);

        Long counter = redisTemplate.opsForValue().increment(key);
        if (counter == null) {
            throw new TrackingGenerationException("Redis counter increment failed for region: " + region);

        }

        String paddedCounter = String.format("%010d", counter);
        log.info("Prefix here {}", prefix);
        String trackingNumber = prefix + originCountryId.toUpperCase() + destinationCountryId.toUpperCase()
                + region.toUpperCase() + paddedCounter;

        log.info("[TRACKING] Generated tracking number: {} at {}", trackingNumber, OffsetDateTime.now());

        return trackingNumber;
    }
}