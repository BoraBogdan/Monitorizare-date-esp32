package com.borabogdan.javaapi.dto;

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
public class GetSensorDhtDataDTO {

    @NotNull
    private double airHumidity;

    @NotNull
    private double temperature;

    @NotNull
    private Timestamp timestamp;

    @NotNull
    private int microcontrollerid;
}
