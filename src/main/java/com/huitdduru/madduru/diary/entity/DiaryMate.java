package com.huitdduru.madduru.diary.entity;

import com.huitdduru.madduru.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "diary_mate")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryMate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

}
