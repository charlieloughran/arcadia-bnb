package com.makers.makersbnb.repository;

import com.makers.makersbnb.model.Space;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;


public interface SpaceRepository extends CrudRepository<Space, Long> {

    Iterable<Space> findByAvailabilityStartLessThanEqualAndAvailabilityEndGreaterThanEqual(
            LocalDate availabilityStart,
            LocalDate availabilityEnd
    );

    List<Space> findByUserId(Long userId);


}
