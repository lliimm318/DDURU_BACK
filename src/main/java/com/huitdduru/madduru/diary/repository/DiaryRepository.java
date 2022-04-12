package com.huitdduru.madduru.diary.repository;

import com.huitdduru.madduru.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Integer> {
}
