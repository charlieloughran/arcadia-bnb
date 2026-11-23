package com.makers.makersbnb.controller;

import com.makers.makersbnb.model.Space;
import com.makers.makersbnb.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
}
