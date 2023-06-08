package com.borabogdan.javaapi.controller;

import com.borabogdan.javaapi.dto.GetSensorDhtDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.SecureRandom;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final SensorDhtRestController sensorDhtController;

    @GetMapping("/menu")
    public String menu(Model model) {
        return "menu";
    }

    @GetMapping("/dhtPage")
    public String dhtPage(Model model) {
        return "dhtPage";
    }

    @GetMapping("/soilPage")
    public String soilPage(Model model) {
        return "soilPage";
    }
}
