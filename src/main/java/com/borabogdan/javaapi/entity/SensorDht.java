package com.borabogdan.javaapi.entity;

import jakarta.validation.constraints.NotNull;


public record SensorDht(@NotNull double airHumidity, @NotNull double temperature) {
}
