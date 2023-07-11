package com.borabogdan.javaapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSoilHumidityRequestDTO {

    @NotNull
    private int soilHumidity;

    @NotNull
    private int microcontrollerID;
}
