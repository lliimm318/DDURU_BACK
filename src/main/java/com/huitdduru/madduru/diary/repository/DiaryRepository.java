package com.huitdduru.madduru.diary.repository;

import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Integer> {

    @Query(value = "select d from Diary d where (d.user1 = ?1 and d.user2 != ?1) or (d.user2 = ?1 and d.user1 != ?1)")
    List<Diary> findByUser1OrUser2(User user);

    Diary findByUser1AndUser2(User user1, User user2);
}
