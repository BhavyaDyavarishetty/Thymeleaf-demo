package com.thymeleaf.controller;

import com.thymeleaf.service.ThymeleafService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thymeleaf")
public class ThymleafController {

    private final ThymeleafService thymeleafService;

    @Autowired
    public ThymleafController(ThymeleafService thymeleafService) {
        this.thymeleafService = thymeleafService;
    }

    @GetMapping(value = "/basic")
    public void sendBasicEmail(){
        thymeleafService.sendEmail();
    }
}
