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

    @GetMapping("/site")
    public String index(Model model) {
        model.addAttribute("nume", "Bogdan");
        model.addAttribute("randomNumber", Math.abs(new SecureRandom().nextInt(1000)));
        List<GetSensorDhtDataDTO> allDhtData = sensorDhtController.getAllDhtData();
        model.addAttribute("lista", allDhtData);
        return "index";
    }

    @GetMapping("/menu")
    public String menu(Model model) {

        return "menu";
    }



}
