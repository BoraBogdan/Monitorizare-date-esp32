package com.borabogdan.javaapi.controller;

import com.borabogdan.javaapi.dto.AddSoilHumidityRequestDTO;
import com.borabogdan.javaapi.dto.AddSoilHumidityResponseDTO;
import com.borabogdan.javaapi.dto.GetSoilHumidityDTO;
import com.borabogdan.javaapi.service.SoilHumidityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/soil")
@RequiredArgsConstructor
public class SoilHumidityController {

    private final SoilHumidityService soilHumidityService;

    @PostMapping("/addSoilData")
    public ResponseEntity<AddSoilHumidityResponseDTO> addSoilData(@RequestBody @Valid AddSoilHumidityRequestDTO request) {
        log.info("addSoilData(): Receiving soil humidity: ");

        AddSoilHumidityResponseDTO response = soilHumidityService.addSoilData(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllSoilData")
    public List<GetSoilHumidityDTO> getAllData() {
        log.info("getAllData() from the soil called");

        return soilHumidityService.getAllSoilData();
    }
}
