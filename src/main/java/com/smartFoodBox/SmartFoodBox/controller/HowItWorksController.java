package com.smartFoodBox.SmartFoodBox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HowItWorksController {

    @GetMapping("/how-it-works")
    public String howItWorks() {
        return "how-it-works";
    }
}
