package com.borabogdan.javaapi.controller;

import com.borabogdan.javaapi.dto.*;
import com.borabogdan.javaapi.service.SoilHumidityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/soil")
@RequiredArgsConstructor
public class SoilHumidityRestController {

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

    @GetMapping("/getAvgLas24Hours")
    public ResponseEntity<List<GetSoilHumidityAvgDTO>> getAvgLast24Hours() {
        List <GetSoilHumidityAvgDTO> response = new ArrayList<>();
        try {
            response = soilHumidityService.getLast24HoursData();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAvgLast7Days")
    public ResponseEntity<List<GetSoilHumidityAvgDTO>> getAvgLast7Days() {
        List <GetSoilHumidityAvgDTO> response = new ArrayList<>();
        try {
            response = soilHumidityService.getLast7DaysData();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
