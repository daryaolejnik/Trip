package com.softserve.kickscootertrip.model;

import com.softserve.kickscootertrip.dto.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "trip")
public class TripEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "tripId")
    private UUID tripId;
    @Column(name = "userId")
    private UUID userId;
    @Column(name = "scooterId")
    private UUID scooterId;
    @Column(name = "start")
    private Instant tripStarts;
    @Column(name = "finish")
    private Instant tripFinishes;
    @Column(name = "tripTime")
    private Duration tripTime;
    @Column(name = "distance")
    private double distance;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TripStatus status;

}
