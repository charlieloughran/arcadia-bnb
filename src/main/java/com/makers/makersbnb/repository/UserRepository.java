package com.makers.makersbnb.repository;

import com.makers.makersbnb.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByZitadelId(String zitadelId);

}
