package com.borabogdan.javaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GetSensorDhtDataAvgDTO {
    private int time;
    private double temperatureAvg;
    private double airHumidityAvg;
}
