package com.softserve.kickscootertrip.service;


import com.softserve.kickscootertrip.dto.Point;
import com.softserve.kickscootertrip.dto.ScooterStatusDto;
import com.softserve.kickscootertrip.dto.TripStatus;
import com.softserve.kickscootertrip.model.Geo;
import com.softserve.kickscootertrip.model.TripEntity;
import com.softserve.kickscootertrip.repository.GeoRepository;
import com.softserve.kickscootertrip.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class GeoService {

    private final GeoRepository geoRepository;
    private final TripRepository tripRepository;

    public Geo save(ScooterStatusDto scooterStatusDto) {
        Geo geo = new Geo();
        TripEntity tripEntity = tripRepository.findByScooterIdAndStatus(scooterStatusDto.getId(), TripStatus.ON_RIDE);
        geo.setTripId(tripEntity.getTripId());
        geo.setLongitude(scooterStatusDto.getGpsPoint().getX());
        geo.setLatitude(scooterStatusDto.getGpsPoint().getY());
        geo.setCreation(Instant.now());
        geoRepository.save(geo);
        return geo;
    }


    public double calculateDistance(UUID tripId) {
        double distance = 0;
        List<Geo> geoTrip = geoRepository.getByTripId(tripId);
        if(geoTrip.isEmpty()) return 0;
        Point previousPoint = new Point(geoTrip.get(0).getLongitude(), geoTrip.get(0).getLatitude());
        Point newPoint;
        for (Geo geo : geoTrip) {
            newPoint = new Point(geo.getLongitude(), geo.getLatitude());
            distance += betweenTwoPoints(previousPoint, newPoint);
            previousPoint = newPoint;
        }
        return distance;
    }

    private double betweenTwoPoints(Point previous, Point next) {
        double R = 6378.137; // Radius of Earth in KM
        double dLat = next.getY() * Math.PI / 180 - previous.getY() * Math.PI / 180;
        double dLon = next.getX() * Math.PI / 180 - previous.getX() * Math.PI / 180;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(previous.getY() * Math.PI / 180) * Math.cos(next.getY() * Math.PI / 180) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d * 1000; // meters
    }
}
