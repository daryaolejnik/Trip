package com.softserve.kickscootertrip.controller;


import com.softserve.kickscootertrip.dto.TripDto;
import com.softserve.kickscootertrip.dto.UIDto;
import com.softserve.kickscootertrip.model.TripEntity;
import com.softserve.kickscootertrip.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
@Slf4j
public class TripController {

    private final TripService tripService;
    private final PaymentClient paymentClient;
    private final VehicleClient vehicleClient;
    private final KafkaTemplate<String, TripDto> kafkaTemplate;

    @Value("${service-token}")
    private String bearerToken;

    @PostMapping("/start")
    public ResponseEntity<UUID> setStartUserInfo(@RequestBody UIDto uiDto) {
        if(tripService.isScooterInUse(uiDto.getScooterId())){
            log.info("Scooter "+uiDto+" is in use now");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (!paymentClient.isUserCanPay(bearerToken, uiDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        }
        vehicleClient.acquireScooter(bearerToken, uiDto.getScooterId());
        return ResponseEntity.ok(tripService.saveStartUserInfo(uiDto).getTripId());
    }

    @PostMapping("/finish")
    public ResponseEntity<UUID> setFinishUserInfo(@RequestBody UIDto uiDto) {
        vehicleClient.freeScooter(bearerToken, uiDto.getScooterId());
        TripDto tripDto = tripService.saveStopUserInfo(uiDto);
        log.info("TripDto for sending " + tripDto);

        kafkaTemplate.send("payment-info", tripDto);
        log.info("send the payment info");

        return ResponseEntity.ok(tripDto.getUserId());
    }

    @GetMapping("{userId}/history")
    public ResponseEntity<List<TripDto>> findUserTrips(@PathVariable UUID userId){
        return ResponseEntity.ok(tripService.getUserTripsDetails(userId));
    }

}
