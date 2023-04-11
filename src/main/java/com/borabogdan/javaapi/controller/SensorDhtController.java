package com.borabogdan.javaapi.controller;

import com.borabogdan.javaapi.dto.GetSensorDhtRequestDTO;
import com.borabogdan.javaapi.dto.GetSensorDhtResponseDTO;

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

    @PostMapping("/getDHT_data")
    public ResponseEntity<GetSensorDhtResponseDTO> getSensorDHT_data(@RequestBody @Valid GetSensorDhtRequestDTO GetSensorDhtRequestDTO) {
        log.info("getSensorDHT_data(): Receiving DHT data");
        GetSensorDhtResponseDTO response = new GetSensorDhtResponseDTO();
        response.setTemperature(GetSensorDhtRequestDTO.getTemperature());
        response.setAirHumidity(GetSensorDhtRequestDTO.getAirHumidity());
        response.setMessage("Data is set");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
