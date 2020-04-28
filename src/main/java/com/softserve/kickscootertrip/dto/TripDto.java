package com.softserve.kickscootertrip.dto;

import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Data
public class TripDto {
    private UUID userId;
    private UUID tripId;
    private Instant tripStarts;
    private Duration tripTime;
    private double distance;

}
