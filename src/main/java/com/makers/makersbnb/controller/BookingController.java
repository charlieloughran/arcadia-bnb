package com.makers.makersbnb.controller;

import com.makers.makersbnb.model.Booking;
import com.makers.makersbnb.model.Space;
import com.makers.makersbnb.model.User;
import com.makers.makersbnb.repository.BookingRepository;
import com.makers.makersbnb.repository.SpaceRepository;
import com.makers.makersbnb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Iterator;
import java.util.List;

@RestController
public class BookingController{

    @Autowired
    SpaceRepository spaceRepository;


    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/bookings")
    public ModelAndView showBookings(@AuthenticationPrincipal OidcUser oidcUser) {
        ModelAndView mav = new ModelAndView("bookings");

        if (oidcUser == null) {
            return new ModelAndView("redirect:/oauth2/authorization/zitadel");
        }

        String zitadelUserId = oidcUser.getSubject();
        String userEmail = oidcUser.getEmail();

        User user = userRepository.findByZitadelId(zitadelUserId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setZitadelId(zitadelUserId);
                    newUser.setEmail(userEmail);
                    return userRepository.save(newUser);
                });

        List<Booking> myBookings = bookingRepository.findByUserId(user.getId());
        List<Space> mySpaces = spaceRepository.findByUserId(user.getId());

        mav.addObject("myBookings", myBookings);
        mav.addObject("mySpaces", mySpaces);

        return mav;
    }
}
