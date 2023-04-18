package com.borabogdan.javaapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "sensor_dht_data")
public class SensorDht {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer ID;
    @NotNull
    @Column(name = "air_humidity")
    double airHumidity;
    @NotNull
    @Column(name = "temperature")
    double temperature;
    @NotNull
    @Column(name = "timestamp")
    Timestamp timestamp;
}