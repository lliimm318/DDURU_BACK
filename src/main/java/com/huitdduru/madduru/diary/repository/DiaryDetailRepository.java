package com.huitdduru.madduru.diary.repository;

import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.diary.entity.DiaryDetail;
import com.huitdduru.madduru.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryDetailRepository extends JpaRepository<DiaryDetail, Integer> {

    DiaryDetail findFirstByDiaryOrderByCreatedAtDesc(Diary diary);

    List<DiaryDetail> findByDiaryOrderByCreatedAt(Diary diary);

    List<DiaryDetail> findByDiaryAndDateOrderByDate(Diary diary, String date);

    List<DiaryDetail> findByDiaryAndDateContainsOrderByDate(Diary diary, String date);

}
