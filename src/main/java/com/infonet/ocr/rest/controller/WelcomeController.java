package com.infonet.ocr.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping("/welcome")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello "+name;
    }
}
