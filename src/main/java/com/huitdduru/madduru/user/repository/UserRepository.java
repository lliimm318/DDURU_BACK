package com.huitdduru.madduru.user.repository;

import com.huitdduru.madduru.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}