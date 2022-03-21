package com.huitdduru.madduru.user.domain.repository;

import com.huitdduru.madduru.user.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByEmail(String userEmail);
}
