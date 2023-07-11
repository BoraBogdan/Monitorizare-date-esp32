package com.borabogdan.javaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
public class GetSoilHumidityAvgDTO {

    private Timestamp time;

    private double soilHumidityAvg;
}
