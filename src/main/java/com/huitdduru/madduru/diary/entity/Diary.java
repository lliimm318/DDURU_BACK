package com.huitdduru.madduru.diary.entity;

import com.huitdduru.madduru.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "diary")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "feeling", nullable = false, length = 1)
    private String feeling;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "contents", nullable = false, length = 2000)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "image_path")
    private String image_path;

}