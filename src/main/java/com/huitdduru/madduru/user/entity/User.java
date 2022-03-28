package com.huitdduru.madduru.user.entity;

import com.huitdduru.madduru.diary.entity.ChangeDiary;
import com.huitdduru.madduru.diary.entity.Diary;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "user")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true, length = 20)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "intro")
    private String intro;

    @Column(name = "image_path")
    private String image_path;

    @Column(name = "code", nullable = false, unique = true, length = 6)
    private String code;

    @Column(name = "is_exist", nullable = false)
    private boolean isExist;

    @OneToMany(mappedBy = "user")
    private final List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private final List<ChangeDiary> myChangeDiaries = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private final List<ChangeDiary> receivedChangeDiaries = new ArrayList<>();

}