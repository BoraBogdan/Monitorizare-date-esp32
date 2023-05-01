package com.borabogdan.javaapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final SensorDhtController sensorDhtController;

    @GetMapping("/site")
    public String index(Model model) {
        model.addAttribute("nume", "Bogdan");
        return "index";
    }



}
