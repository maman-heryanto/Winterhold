package com.winterhold.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/landingPage")
public class LandingPageController {
    @GetMapping("/index")
    public String index(){ //Action Method
        return "landing-page";
    }
}
