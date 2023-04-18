package com.borabogdan.javaapi.service;

import com.borabogdan.javaapi.dto.AddSensorDhtRequestDTO;
import com.borabogdan.javaapi.dto.AddSensorDhtResponseDTO;
import com.borabogdan.javaapi.dto.GetSensorDhtDataDTO;
import com.borabogdan.javaapi.entity.SensorDht;
import com.borabogdan.javaapi.repository.SensorDhtRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class SensorDhtService {

    @Autowired
    SensorDhtRepository sensorDhtRepository;

    public AddSensorDhtResponseDTO addDhtData (AddSensorDhtRequestDTO request) {

        SensorDht dataToBeInserted = SensorDht.builder()
                .airHumidity(request.getAirHumidity())
                .temperature(request.getTemperature())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();

        SensorDht dataFromDB = sensorDhtRepository.save(dataToBeInserted);

        AddSensorDhtResponseDTO response = new AddSensorDhtResponseDTO();
        response.setAirHumidity(dataFromDB.getAirHumidity());
        response.setTemperature(dataFromDB.getTemperature());
        response.setMessage("Data inserted in DB");

        return response;
    }

    public List<GetSensorDhtDataDTO> getAlldhtData() {
        return sensorDhtRepository.findAll().stream()
                .map(row -> GetSensorDhtDataDTO.builder()
                        .airHumidity(row.getAirHumidity())
                        .temperature(row.getTemperature())
                        .timestamp(row.getTimestamp())
                        .build())
                .collect(Collectors.toList());
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


}