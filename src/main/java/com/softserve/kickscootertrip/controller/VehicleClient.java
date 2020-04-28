package com.softserve.kickscootertrip.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "vehicle-service")
public interface VehicleClient {

    @PutMapping(path = "/scooters/status/{scooterId}/acquire")
    String acquireScooter(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken, @PathVariable UUID scooterId);

    @PutMapping(path = "/scooters/status/{scooterId}/free")
    String freeScooter(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken, @PathVariable UUID scooterId);

}
