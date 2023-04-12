package com.borabogdan.javaapi.controller;

import com.borabogdan.javaapi.dto.AddSensorDhtRequestDTO;
import com.borabogdan.javaapi.dto.AddSensorDhtResponseDTO;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
public class SensorDhtController {

    @PostMapping("/addDhtData")
    public ResponseEntity<AddSensorDhtResponseDTO> addSensorDhtData(@RequestBody @Valid AddSensorDhtRequestDTO AddSensorDhtRequestDTO) {
        log.info("addSensorDhtData(): Receiving DHT data");
        AddSensorDhtResponseDTO response = new AddSensorDhtResponseDTO();
        response.setTemperature(AddSensorDhtRequestDTO.getTemperature());
        response.setAirHumidity(AddSensorDhtRequestDTO.getAirHumidity());
        response.setMessage("Data is set");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
