package com.makers.makersbnb.controller;

import com.makers.makersbnb.model.Booking;
import com.makers.makersbnb.model.Space;
import com.makers.makersbnb.repository.BookingRepository;
import com.makers.makersbnb.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class BookingController{

    @Autowired
    SpaceRepository spaceRepository;


    @Autowired
    BookingRepository bookingRepository;

    @PostMapping("/spaces/{id}/book")
    public RedirectView bookSpace(@PathVariable Long id, Booking booking, Authentication authentication){
        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Space not found"));

        booking.setId(null);
        String userId = authentication.getName();

        booking.setSpace(space);
        booking.setUserId(userId);

        bookingRepository.save(booking);
        return new RedirectView("/");
    }
}
