package com.softserve.kickscootertrip.converters;

import com.softserve.kickscootertrip.dto.TripDto;
import com.softserve.kickscootertrip.model.TripEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TripToTripDto implements Converter<TripEntity, TripDto> {

    @Override
    public TripDto convert(TripEntity tripEntity) {
        TripDto tripDto = new TripDto();
        tripDto.setUserId(tripEntity.getUserId());
        tripDto.setTripId(tripEntity.getTripId());
        tripDto.setTripStarts(tripEntity.getTripStarts());
        tripDto.setTripTime(tripEntity.getTripTime());
        tripDto.setDistance(tripEntity.getDistance());
        return tripDto;
    }
}
