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
public class SensorDhtEntity extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer ID;

    @NotNull
    @Column(name = "air_humidity")
    private double airHumidity;

    @NotNull
    @Column(name = "temperature")
    private double temperature;

    @NotNull
    @Column(name = "timestamp")
    private Timestamp timestamp;
}