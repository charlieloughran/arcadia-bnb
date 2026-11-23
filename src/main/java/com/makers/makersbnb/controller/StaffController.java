package com.makers.makersbnb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class StaffController {
    @GetMapping("/team")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/Team");

        String[] team = new String[] {"Toby", "Katerina", "Sandy"};

        modelAndView.addObject("team", team);
        return modelAndView;
    }
}
