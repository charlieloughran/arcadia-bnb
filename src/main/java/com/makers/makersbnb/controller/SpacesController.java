package com.makers.makersbnb.controller;

import com.makers.makersbnb.model.Booking;
import com.makers.makersbnb.model.Space;
import com.makers.makersbnb.model.User;
import com.makers.makersbnb.repository.UserRepository;
import com.makers.makersbnb.repository.BookingRepository;
import com.makers.makersbnb.repository.SpaceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class SpacesController {

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;


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
    public ModelAndView getSpaceDetails(@PathVariable Long id) {
        ModelAndView spaceDetails = new ModelAndView("spaces/SpaceDetails");


//        Space space = spaceRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Space not found"));


        Space space = spaceRepository.findById(id).orElse(null);

        if (space != null) {
            spaceDetails.addObject("space", space);
        }

        System.out.println("Looking for space with ID: " + id);
        System.out.println(space.getName());


        // NOTE: add each details as a separate object to render in thymleaf

        return spaceDetails;
    }

    @GetMapping("/spaces/searchByDate")
    public ModelAndView searchSpaces(@RequestParam("availability") LocalDate availability) {
         ModelAndView filteredSpaces = new ModelAndView("/spaces");

         Iterable<Space> spaces = spaceRepository.findByAvailabilityStartLessThanEqualAndAvailabilityEndGreaterThanEqual(availability, availability);

        filteredSpaces.addObject("spaces", spaces);

        return filteredSpaces;
    }

    @GetMapping("/spaces/searchByMultipleDates")
    public ModelAndView searchSpacesBetweenDates(@RequestParam("availability_start") LocalDate availability_start, @RequestParam("availability_end") LocalDate availability_end) {
        ModelAndView filteredSpaces = new ModelAndView("/spaces");

        Iterable<Space> spaces = spaceRepository.findByAvailabilityStartLessThanEqualAndAvailabilityEndGreaterThanEqual(availability_start, availability_end);

        filteredSpaces.addObject("spaces", spaces);

        return filteredSpaces;
    }

    @PostMapping("/spaces/{id}/book")
    public RedirectView bookSpace(@PathVariable Long id, Booking booking, Authentication authentication){
        if (authentication == null){
            return new RedirectView("/");
        }

        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        String zitadelUserId = oidcUser.getSubject();
        String userEmail = oidcUser.getEmail();

        User user = userRepository.findByZitadelId(zitadelUserId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setZitadelId(zitadelUserId);
                    newUser.setEmail(userEmail);
                    return userRepository.save(newUser);
                });
        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Space not found"));

        String userId = authentication.getName();

        booking.setId(null);
        booking.setSpace(space);
        booking.setUserId(user.getId());

        // Check if dates are null before doing date comparisons
        if (booking.getBooked_from() == null || booking.getBooked_until() == null) {
            System.out.println("Booking dates cannot be null");
            return new RedirectView("/spaces/" + id);
        }

        // Check if dates are null before doing date comparisons
        if (booking.getBooked_from().isAfter(booking.getBooked_until())) {
            System.out.println("Date cannot be before");
            return new RedirectView("/spaces/" + id);
        }

        // Check if booking is outside space availability dates
        if (booking.getBooked_from().isBefore(space.getAvailabilityStart()) || booking.getBooked_until().isAfter(space.getAvailabilityEnd())) {
            System.out.println("Space is not available between these dates");
            return new RedirectView("/");
        }

        // Check each booking and see if there is a clash in dates
        Iterable<Booking> currentBookings = bookingRepository.findAll();
        boolean hasClash = false;

        for (Booking existing : currentBookings) {
            // Only check bookings for this specific space
            if (existing.getSpace().getId().equals(space.getId())) {

                // Check for date overlap
                boolean overlaps = booking.getBooked_from().isBefore(existing.getBooked_until())
                        && booking.getBooked_until().isAfter(existing.getBooked_from());

                if (overlaps) {
                    hasClash = true;
                    System.out.println("There's a clash");
                    break;
                }
            }
        }

        if (hasClash) {
            String message = "Space is booked for these dates already";
            System.out.println(message);
            return new RedirectView("/spaces/" + id);
        }

        // If no overlaps, save the booking
        bookingRepository.save(booking);
        System.out.println("YOU'RE IN!");

        return new RedirectView("/");
    }
}
