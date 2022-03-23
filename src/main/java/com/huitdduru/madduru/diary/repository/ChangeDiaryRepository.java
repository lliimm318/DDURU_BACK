package com.huitdduru.madduru.diary.repository;

import com.huitdduru.madduru.diary.entity.ChangeDiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeDiaryRepository extends JpaRepository<ChangeDiary, Integer> {
}