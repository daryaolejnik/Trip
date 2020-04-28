package com.softserve.kickscootertrip.controller;

import com.softserve.kickscootertrip.dto.TripDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "payment-service")
public interface PaymentClient {

    @GetMapping(path = "/payments/{userId}/user-solvency")
    Boolean isUserCanPay(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken, @PathVariable UUID userId);

}


