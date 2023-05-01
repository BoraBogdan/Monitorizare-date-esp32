package com.borabogdan.javaapi.controller;

import com.borabogdan.javaapi.dto.AddSensorDhtRequestDTO;
import com.borabogdan.javaapi.dto.AddSensorDhtResponseDTO;

import com.borabogdan.javaapi.dto.GetAvailableDaysDTO;
import com.borabogdan.javaapi.dto.GetSensorDhtDataDTO;
import com.borabogdan.javaapi.service.SensorDhtService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Log4j2
@RestController
@RequestMapping("/dht")
@RequiredArgsConstructor
public class SensorDhtController {

    private final SensorDhtService sensorDhtService;

    @PostMapping("/addData")
    public ResponseEntity<AddSensorDhtResponseDTO> addDhtData(@RequestBody @Valid AddSensorDhtRequestDTO request) {
        log.info("addDhtData(): Receiving DHT data");

        AddSensorDhtResponseDTO response = sensorDhtService.addDhtData(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllData")
    public List<GetSensorDhtDataDTO> getAllDhtData() {
        log.info("getAllDhtData(): Getting all the data from the DB");
        return sensorDhtService.getAllDhtData();
    }

    @DeleteMapping("/deleteAllData")
    public ResponseEntity<String> deleteAllDhtData() {
        log.info("deleteAllDhtData(): Deleting all the data from DB");
        ResponseEntity<String> deleteSuccess = new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        ResponseEntity<String> deleteFailed = new ResponseEntity<>("Deletion failed", HttpStatus.BAD_REQUEST);

        return sensorDhtService.deleteAllData() ? deleteSuccess : deleteFailed;
    }

    @GetMapping("/getDataFromDay{day}")
    public ResponseEntity<List<GetSensorDhtDataDTO>> getDataByDay(@PathVariable("day") int day) {
        List<GetSensorDhtDataDTO> response = new ArrayList<>();
        try {
            response = sensorDhtService.getDhtDataFromDay(day);
        } catch (EntityNotFoundException ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        log.info("getDataByDay(): Fetching data from day " + day);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAvailableDays")
    public ResponseEntity<List<GetAvailableDaysDTO>> getAllAvailableDays() {
        List <GetAvailableDaysDTO> response = new ArrayList<>();
        try {
            response = sensorDhtService.getAllAvailableDays();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
