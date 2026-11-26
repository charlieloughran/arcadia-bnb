package com.makers.makersbnb.repository;

import com.makers.makersbnb.model.Staff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;


public interface StaffRepository extends CrudRepository<Staff, Long> {
}
