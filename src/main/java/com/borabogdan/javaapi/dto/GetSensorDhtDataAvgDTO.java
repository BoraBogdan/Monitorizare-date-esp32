package com.borabogdan.javaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
public class GetSensorDhtDataAvgDTO {

    private Timestamp time;

    private double temperatureAvg;

    private double airHumidityAvg;
}
