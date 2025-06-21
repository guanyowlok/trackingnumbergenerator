package com.tracking.trackinggenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingResponse {
    private String trackingNumber;
    private String generatedAt;
}