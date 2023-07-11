package com.borabogdan.javaapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetSoilHumidityDTO {

    @NotNull
    private int soilHumidity;

    @NotNull
    private Timestamp timestamp;

    @NotNull
    private int microcontrollerid;
}
