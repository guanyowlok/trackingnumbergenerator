package com.tracking.trackinggenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingRequest {
    private String originCountryId;
    private String destinationCountryId;
}
