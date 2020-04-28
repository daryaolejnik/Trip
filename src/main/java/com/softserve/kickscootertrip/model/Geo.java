package com.softserve.kickscootertrip.model;

import com.softserve.kickscootertrip.dto.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "geolocation")
public class Geo{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private UUID tripId;
        private double longitude;
        private double latitude;
        private Instant creation;
}
