package com.smartFoodBox.SmartFoodBox.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MealsController {

    @GetMapping("/meals")
    public String meals() {
        return "meals";
    }
}
