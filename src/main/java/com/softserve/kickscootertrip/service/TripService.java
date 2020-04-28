package com.softserve.kickscootertrip.service;


import com.softserve.kickscootertrip.dto.TripDto;
import com.softserve.kickscootertrip.dto.TripStatus;
import com.softserve.kickscootertrip.dto.UIDto;
import com.softserve.kickscootertrip.model.TripEntity;
import com.softserve.kickscootertrip.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class TripService {

    private final TripRepository tripRepository;
    private final GeoService geoService;
    private final ConversionService conversionService;

    public TripEntity saveStartUserInfo(UIDto UIDto) {
        TripEntity tripEntity = new TripEntity();
        tripEntity.setUserId(UIDto.getUserId());
        tripEntity.setScooterId(UIDto.getScooterId());
        tripEntity.setTripStarts(Instant.now());
        tripEntity.setStatus(TripStatus.ON_RIDE);
        return tripRepository.save(tripEntity);
    }

    public TripDto saveStopUserInfo(UIDto UIDto) {
        UUID scooterId = UIDto.getScooterId();
        TripEntity tripEntity = tripRepository.findByScooterIdAndStatus(scooterId, TripStatus.ON_RIDE);

        tripEntity.setTripFinishes(Instant.now());
        Duration tripTime = Duration.between(tripEntity.getTripStarts(), Instant.now());
        tripEntity.setTripTime(tripTime);
        tripEntity.setStatus(TripStatus.FREE);
        tripEntity.setDistance(geoService.calculateDistance(tripEntity.getTripId()));
        tripRepository.save(tripEntity);

        TripDto tripDto = conversionService.convert(tripEntity, TripDto.class);
        return tripDto;
    }

    public List<TripDto> getUserTripsDetails(UUID userId) {
        List<TripEntity> trips;
        trips = tripRepository.findByUserId(userId);
        List<TripDto> tripsDto = new ArrayList<>();
        for (TripEntity trip : trips) {
            TripDto tripDto = conversionService.convert(trip, TripDto.class);
            tripsDto.add(tripDto);
        }
        return tripsDto;
    }

    public boolean isScooterInUse(UUID scooterId){
        return null != tripRepository.findByScooterIdAndStatus(scooterId, TripStatus.ON_RIDE);
    }
}
