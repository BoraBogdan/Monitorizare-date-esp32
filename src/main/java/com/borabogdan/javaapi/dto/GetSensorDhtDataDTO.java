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
    double airHumidity;

    @NotNull
    double temperature;

    @NotNull
    Timestamp timestamp;
}
