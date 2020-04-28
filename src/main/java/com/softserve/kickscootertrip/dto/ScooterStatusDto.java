package com.softserve.kickscootertrip.dto;

import lombok.Data;


import java.util.UUID;

@Data
public class ScooterStatusDto {

    UUID id;
    Point gpsPoint;
    Short battery;
}
