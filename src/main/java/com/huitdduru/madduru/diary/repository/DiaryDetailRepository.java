package com.huitdduru.madduru.diary.repository;

import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.diary.entity.DiaryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryDetailRepository extends JpaRepository<DiaryDetail, Integer> {
    DiaryDetail findFirstByDiaryOrderByCreatedAtDesc(Diary diary);
}
