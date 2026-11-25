package com.makers.makersbnb.controller;

import com.makers.makersbnb.model.Space;
import com.makers.makersbnb.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;

@RestController
public class SpacesController {

    @Autowired
    SpaceRepository spaceRepository;


    @GetMapping("/spaces")
    public ModelAndView spaces() {
        ModelAndView modelAndView = new ModelAndView("/Spaces");

        Iterable<Space> spaces = spaceRepository.findAll();
        modelAndView.addObject("spaces", spaces);

        return modelAndView;
    }

    @GetMapping("/spaces/new")
    public ModelAndView newSpaceForm() {
        Space space = new Space();
        ModelAndView newSpaceForm = new ModelAndView("spaces/New");

        newSpaceForm.addObject("space", space);

        return newSpaceForm;
    }

    @PostMapping("/spaces")
    public RedirectView create(Space space) {
        spaceRepository.save(space);

        return new RedirectView("/spaces");
    }

    @GetMapping("/spaces/{id}")
    public ModelAndView getSpaceDetails(@PathVariable Long id, Space space) {
        ModelAndView spaceDetails = new ModelAndView("spaces/SpaceDetails");

        spaceDetails.addObject("spaceDetails", space);

        return spaceDetails;

    }

    @GetMapping("/spaces/search")
    public ModelAndView searchSpaces(@RequestParam("availability") LocalDate availability) {
         ModelAndView filteredSpaces = new ModelAndView("/spaces");

         Iterable<Space> spaces = spaceRepository.findByAvailability( availability);

        filteredSpaces.addObject("spaces", spaces);

        return filteredSpaces;
    }

    @GetMapping("/spaces/searchByStartAndEnd")
    public ModelAndView searchSpacesBetweenDates(@RequestParam("availability_start") LocalDate availability_start, @RequestParam("availability_end") LocalDate availability_end) {
        ModelAndView filteredSpaces = new ModelAndView("/spaces");

        Iterable<Space> spaces = spaceRepository.findByAvailabilityStartAndEnd(availability_start, availability_end);

        filteredSpaces.addObject("spaces", spaces);

        return filteredSpaces;
    }
}
