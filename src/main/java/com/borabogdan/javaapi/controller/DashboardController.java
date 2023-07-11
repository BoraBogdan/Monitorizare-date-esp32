package com.borabogdan.javaapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

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
