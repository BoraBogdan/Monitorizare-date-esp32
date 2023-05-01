package com.borabogdan.javaapi.service;

import com.borabogdan.javaapi.dto.AddSensorDhtRequestDTO;
import com.borabogdan.javaapi.dto.AddSensorDhtResponseDTO;
import com.borabogdan.javaapi.dto.GetAvailableDaysDTO;
import com.borabogdan.javaapi.dto.GetSensorDhtDataDTO;
import com.borabogdan.javaapi.entity.SensorDhtEntity;
import com.borabogdan.javaapi.repository.SensorDhtRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class SensorDhtService {

   private final SensorDhtRepository sensorDhtRepository;


    public AddSensorDhtResponseDTO addDhtData(AddSensorDhtRequestDTO request) {

        SensorDhtEntity dataToBeInserted = SensorDhtEntity.builder()
                .airHumidity(request.getAirHumidity())
                .temperature(request.getTemperature())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        dataToBeInserted.setMicrocontrollerID(request.getMicrocontrollerID());

        SensorDhtEntity dataFromDB = sensorDhtRepository.save(dataToBeInserted);

        AddSensorDhtResponseDTO response = new AddSensorDhtResponseDTO();
        response.setAirHumidity(dataFromDB.getAirHumidity());
        response.setTemperature(dataFromDB.getTemperature());
        response.setMessage("Data inserted in DB");

        return response;
    }

    public List<GetSensorDhtDataDTO> getAllDhtData() {
        return sensorDhtRepository.findAll().stream()
                .map(row -> GetSensorDhtDataDTO.builder()
                        .airHumidity(row.getAirHumidity())
                        .temperature(row.getTemperature())
                        .timestamp(row.getTimestamp())
                        .microcontrollerid(row.getMicrocontrollerID())
                        .build())
                .toList();
    }

    public boolean deleteAllData() {
        try {
            sensorDhtRepository.deleteAll();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }
        return true;
    }

    public List<GetSensorDhtDataDTO> getDhtDataFromDay(int day) {

        List<GetSensorDhtDataDTO> elementsFromDB = new ArrayList<>();

        sensorDhtRepository.findByDay(day).ifPresentOrElse(
                list -> {
                    if (list.isEmpty()) {
                        throw new EntityNotFoundException("No entries on the day = " + day);
                    }
                    list.stream()
                            .map(row -> GetSensorDhtDataDTO.builder()
                                    .airHumidity(row.getAirHumidity())
                                    .temperature(row.getTemperature())
                                    .timestamp(row.getTimestamp())
                                    .microcontrollerid(row.getMicrocontrollerID())
                                    .build()).forEach(elementsFromDB::add);
                }
                ,
                () -> {
                    throw new EntityNotFoundException("Error while fetching from DB");
                }
        );

        return elementsFromDB;
    }

    public List<GetAvailableDaysDTO> getAllAvailableDays() {
        return sensorDhtRepository.getAllTimestamps().stream()
                .map(row -> GetAvailableDaysDTO.builder()
                        .timestamp(row)
                        .build())
                .toList();
    }
}