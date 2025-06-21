package com.tracking.trackinggenerator.controller;

import com.tracking.trackinggenerator.dto.TrackingRequest;
import com.tracking.trackinggenerator.dto.TrackingResponse;
import com.tracking.trackinggenerator.service.TrackingNumberService;

import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/tracking")
public class TrackingController {

    private TrackingNumberService trackingNumberService;

    public TrackingController(TrackingNumberService trackingNumberService){
        this.trackingNumberService=trackingNumberService;
    }

    @PostMapping("/generate")
    public TrackingResponse generateTrackingNumber(@RequestBody TrackingRequest request) {
        String trackingNumber = trackingNumberService.generateTrackingNumber(request.getOriginCountryId(), request.getDestinationCountryId());
        String timestamp = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return new TrackingResponse(trackingNumber, timestamp);
    }

    @GetMapping("/ping")
        public String hello() {
        return "App is running";
    }

       @GetMapping("/")
        public String helloWor() {
        return "App is running";
    }
}