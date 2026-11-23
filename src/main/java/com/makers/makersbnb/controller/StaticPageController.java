package com.makers.makersbnb.controller;

import com.makers.makersbnb.model.Space;
import com.makers.makersbnb.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class StaticPageController {

    @Autowired
    SpaceRepository spaceRepository;

    @GetMapping("/")
    public ModelAndView landingPage() {
        ModelAndView modelAndView = new ModelAndView("/LandingPage");

        long nSpaces = spaceRepository.count();


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

    @GetMapping("/spaces")
    public ModelAndView spaces() {
        ModelAndView modelAndView = new ModelAndView("/Spaces");

        Iterable<Space> spaces = spaceRepository.findAll();
        modelAndView.addObject("spaces", spaces);


        return modelAndView;
    }
}
