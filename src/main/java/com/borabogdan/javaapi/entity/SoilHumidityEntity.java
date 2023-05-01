package com.borabogdan.javaapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sensor_soil_humidity")
public class SoilHumidityEntity extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer ID;

    @NotNull
    @Column(name = "soil_humidity")
    private int soilHumidity;

    @NotNull
    @Column(name = "timestamp")
    private Timestamp timestamp;

}
