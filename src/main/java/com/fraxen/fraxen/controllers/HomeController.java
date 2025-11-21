package com.fraxen.fraxen.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";  // loads templates/index.html
    }

    @GetMapping("/executives")
    public String executives() {
        return "executives";  // loads templates/executives.html
    }
}
