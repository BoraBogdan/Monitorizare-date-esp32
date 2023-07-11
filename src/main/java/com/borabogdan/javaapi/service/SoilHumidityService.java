package com.borabogdan.javaapi.service;

import com.borabogdan.javaapi.dto.*;
import com.borabogdan.javaapi.entity.SoilHumidityEntity;
import com.borabogdan.javaapi.repository.SoilHumidityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SoilHumidityService {

    private final SoilHumidityRepository soilHumidityRepository;

    public AddSoilHumidityResponseDTO addSoilData(AddSoilHumidityRequestDTO request) {

        SoilHumidityEntity dataToBeInserted = SoilHumidityEntity.builder()
                .soilHumidity(request.getSoilHumidity())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        dataToBeInserted.setMicrocontrollerID(request.getMicrocontrollerID());

        SoilHumidityEntity dataFromDB = soilHumidityRepository.save(dataToBeInserted);

        return AddSoilHumidityResponseDTO.builder()
                .soilHumidity(dataFromDB.getSoilHumidity())
                .message("Soil humidity inserted in DB")
                .build();
    }

    public List<GetSoilHumidityDTO> getAllSoilData() {
        return soilHumidityRepository.findAll().stream()
                .map(row -> GetSoilHumidityDTO.builder()
                        .soilHumidity(row.getSoilHumidity())
                        .timestamp(row.getTimestamp())
                        .microcontrollerid(row.getMicrocontrollerID())
                        .build())
                .toList();
    }

    public List<GetSoilHumidityAvgDTO> getLast24HoursData() {
        Instant instant = Instant.now().minus(24, ChronoUnit.HOURS);
        Timestamp tsp = Timestamp.from(instant);
        return soilHumidityRepository.getLast24HoursData(tsp);
    }

    public List<GetSoilHumidityAvgDTO> getLast7DaysData() {
        Instant instant = Instant.now().minus(7, ChronoUnit.DAYS);
        Timestamp tsp = Timestamp.from(instant);
        return soilHumidityRepository.getLast7DaysData(tsp);
    }
}
