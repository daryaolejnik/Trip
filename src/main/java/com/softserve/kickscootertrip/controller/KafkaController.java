package com.softserve.kickscootertrip.controller;

import com.softserve.kickscootertrip.dto.ScooterStatusDto;
import com.softserve.kickscootertrip.service.GeoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KafkaController {
    private final GeoService geoService;
    private static final String IN_USE = "in-use-scooter-data";

    @KafkaListener(topics = IN_USE)
    public void consumer(ScooterStatusDto scooterStatusDto) {
        log.info(scooterStatusDto + " received");
        geoService.save(scooterStatusDto);
    }
}
