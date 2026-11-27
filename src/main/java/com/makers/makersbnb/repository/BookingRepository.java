package com.makers.makersbnb.repository;

import com.makers.makersbnb.model.Booking;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface BookingRepository extends CrudRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
}
