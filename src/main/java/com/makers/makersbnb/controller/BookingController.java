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

import java.util.Iterator;

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

        String userId = "123123";

        booking.setId(null);
        booking.setSpace(space);
        booking.setUserId(userId);

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
        if (booking.getBooked_from().isBefore(space.getAvailability_start()) || booking.getBooked_until().isAfter(space.getAvailability_end())) {
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
