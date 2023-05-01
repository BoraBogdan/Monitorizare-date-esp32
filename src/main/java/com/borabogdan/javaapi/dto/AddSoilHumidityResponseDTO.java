package com.borabogdan.javaapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddSoilHumidityResponseDTO {

    @NotNull
    private int soilHumidity;

    private String message;
}
