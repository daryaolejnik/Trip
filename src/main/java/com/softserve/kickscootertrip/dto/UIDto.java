package com.softserve.kickscootertrip.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UIDto {
    private UUID userId;
    private UUID scooterId;
    private TripStatus status;

}
