package com.borabogdan.javaapi.controller;

import com.borabogdan.javaapi.dto.GetSensorDHT_DTO;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
public class SensorDHT_Controller {

    @PostMapping("/getDHT_data")
    public ResponseEntity<GetSensorDHT_DTO> getSensorDHT_data(@RequestBody @Valid GetSensorDHT_DTO getSensorDHT_DTO) {
        log.info("getSensorDHT_data(): Receiving DHT data");
        GetSensorDHT_DTO response = new GetSensorDHT_DTO();
        response.setTemperature(getSensorDHT_DTO.getTemperature());
        response.setAirHumidity(getSensorDHT_DTO.getAirHumidity());
        response.setMessage("Data is set");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
