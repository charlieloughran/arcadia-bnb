package com.makers.makersbnb.repository;

import com.makers.makersbnb.model.Space;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;


public interface SpaceRepository extends CrudRepository<Space, Long> {

    @Query("SELECT row FROM Space row WHERE :availability BETWEEN row.availability_start AND row.availability_end")
    Iterable<Space> findByAvailability(@Param("availability") LocalDate availability);

    @Query("SELECT row FROM Space row WHERE row.availability_start <= :availability_start AND row.availability_end >= :availability_end")
    Iterable<Space> findByAvailabilityStartAndEnd(@Param("availability_start") LocalDate availability_start, @Param("availability_end") LocalDate availability_end);

}
