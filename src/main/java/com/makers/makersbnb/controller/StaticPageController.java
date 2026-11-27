package com.makers.makersbnb.controller;

import com.makers.makersbnb.model.Space;
import com.makers.makersbnb.repository.BookingRepository;
import com.makers.makersbnb.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class StaticPageController {


    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    BookingRepository bookingRepository;


    @GetMapping("/")
    public ModelAndView landingPage() {
        ModelAndView modelAndView = new ModelAndView("/LandingPage");

        long nSpaces = spaceRepository.count();


        modelAndView.addObject("nSpaces", nSpaces);


        Iterable<Space> spaces = spaceRepository.findAll();
        modelAndView.addObject("spaces", spaces);

        String [] reviews = new String[] {"Awesome", "Nice", "Perfect"};
        modelAndView.addObject("reviews", reviews);

        Long nBookings = bookingRepository.count();
        modelAndView.addObject("nBookings", nBookings);

        return modelAndView;
    }

    @GetMapping("/contactus")
    public ModelAndView contactus() {
        ModelAndView modelAndView = new ModelAndView("/ContactUs");

        String emailAddress = "support@arcadiabnb.com";
        modelAndView.addObject("emailAddress", emailAddress);


        return modelAndView;
    }

}
