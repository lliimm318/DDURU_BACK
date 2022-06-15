package com.huitdduru.madduru.user.entity;

import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.diary.entity.DiaryDetail;
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
    private String imagePath;

    @Column(name = "code", nullable = false, unique = true, length = 6)
    private String code;

    @Column(name = "is_exist", nullable = false)
    private boolean isExist;

    @OneToMany(mappedBy = "user1", orphanRemoval = true)
    private final List<Diary> diaries1 = new ArrayList<>();

    @OneToMany(mappedBy = "user2", orphanRemoval = true)
    private final List<Diary> diaries2 = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private final List<DiaryDetail> diaryDetails = new ArrayList<>();

    public User setIntro(String intro) {
        this.intro = intro;
        return this;
    }

    public User setImageUrl(String imageUrl) {
        this.imagePath = imageUrl;
        return this;
    }

    public User unregister() {
        this.isExist = false;
        return this;
    }

    public String getStringId() {
        return this.getId().toString();
    }

    public User setMyInfos(String intro, String imageUrl) {
        this.intro = intro;
        this.imagePath = imageUrl;
        return this;
    }
}