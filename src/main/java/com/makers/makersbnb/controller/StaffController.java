package com.makers.makersbnb.controller;

import com.makers.makersbnb.model.Staff;
import com.makers.makersbnb.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class StaffController {

    @Autowired
    StaffRepository staffRepository;



    @GetMapping("/team")
    public ModelAndView teamList() {
        ModelAndView modelAndView = new ModelAndView("/Team");

        Iterable<Staff> all_staff = staffRepository.findAll();

        modelAndView.addObject("staff", all_staff);
        return modelAndView;
    }
}
