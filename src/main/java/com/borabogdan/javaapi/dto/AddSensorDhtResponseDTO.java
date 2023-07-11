package com.borabogdan.javaapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AddSensorDhtResponseDTO {

    @NotNull
    private double airHumidity;

    @NotNull
    private double temperature;

    private String message;
}
