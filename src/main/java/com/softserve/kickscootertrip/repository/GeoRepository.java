package com.softserve.kickscootertrip.repository;

import com.softserve.kickscootertrip.dto.TripStatus;
import com.softserve.kickscootertrip.model.Geo;
import com.softserve.kickscootertrip.model.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GeoRepository extends JpaRepository<Geo, UUID> {
public List<Geo> getByTripId(UUID tripId);
}
