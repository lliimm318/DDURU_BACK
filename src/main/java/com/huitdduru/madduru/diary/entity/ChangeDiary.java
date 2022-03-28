package com.huitdduru.madduru.diary.entity;

import com.huitdduru.madduru.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "change_diary")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeDiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @OneToOne(optional = false)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @Column(name = "date")
    private LocalDateTime date;

}