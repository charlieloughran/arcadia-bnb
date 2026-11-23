package com.makers.makersbnb.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class StaticPageController {
    @GetMapping("/")
    public ModelAndView landingPage() {
        ModelAndView modelAndView = new ModelAndView("/LandingPage");

        Integer nSpaces = 100;
        modelAndView.addObject("nSpaces", nSpaces);

        String [] reviews = new String[] {"Awesome", "Nice", "Perfect"};
        modelAndView.addObject("reviews", reviews);

        Integer nBookings = 123;
        modelAndView.addObject("nBookings", nBookings);

        return modelAndView;
    }

    @GetMapping("/contactus")
    public ModelAndView contactus() {
        ModelAndView modelAndView = new ModelAndView("/ContactUs");

        String emailAddress = "example@makersbnb.com";
        modelAndView.addObject("emailAddress", emailAddress);


        return modelAndView;
    }
}
