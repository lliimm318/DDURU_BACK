package com.huitdduru.madduru.email.repository;

import com.huitdduru.madduru.email.entity.RandomCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RandomCodeRepository extends CrudRepository<RandomCode, String> {
    Optional<RandomCode> findByEmail(String email);
}
