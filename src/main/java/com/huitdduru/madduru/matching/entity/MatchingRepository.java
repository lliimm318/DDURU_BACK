package com.huitdduru.madduru.matching.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchingRepository extends JpaRepository<Matching, MatchingId> {
    Optional<Matching> findByName(String name);
}