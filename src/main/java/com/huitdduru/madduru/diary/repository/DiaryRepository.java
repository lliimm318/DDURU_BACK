package com.huitdduru.madduru.diary.repository;

import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {

    List<Diary> findByUser1OrUser2(User user);

    Optional<Diary> findByUser1OrUser2AndRelationContinuesIsTrue(User user);

}
