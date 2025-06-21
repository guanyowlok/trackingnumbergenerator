package com.tracking.trackinggenerator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.tracking.trackinggenerator.exception.TrackingGenerationException;

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

    public String generateTrackingNumber(String originCountryId, String destinationCountryId) {
        log.info(
                "Received request to generate tracking number for originCountryId : {}, destinationCountryId : {} for region : {}",
                originCountryId, destinationCountryId, region);
        String key = String.format("tracking:counter:%s", region);

        Long counter = redisTemplate.opsForValue().increment(key);
        if (counter == null) {
            log.error("Unable to increase redis counter");
            throw new TrackingGenerationException("Redis counter increment failed for region: " + region);
        }

        String paddedCounter = String.format("%010d", counter);
        String trackingNumber = prefix + originCountryId.toUpperCase() + destinationCountryId.toUpperCase()
                + region.toUpperCase() + paddedCounter;

        log.info("[TRACKING] Generated tracking number: {} at {}", trackingNumber, OffsetDateTime.now());

        return trackingNumber;
    }
}