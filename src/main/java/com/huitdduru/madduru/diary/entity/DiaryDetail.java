package com.huitdduru.madduru.diary.entity;

import com.huitdduru.madduru.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "diary_detail")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "feeling", nullable = false, length = 1)
    private String feeling;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;
    
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "content", nullable = false, length = 2000)
    private String content;

    @Column(name = "image_path")
    private String image_path;

}
