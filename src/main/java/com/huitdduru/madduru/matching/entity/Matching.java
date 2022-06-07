package com.huitdduru.madduru.matching.entity;

import com.huitdduru.madduru.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "matching")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matching {
    @EmbeddedId
    private MatchingId matchingId;

    @MapsId("user1")
    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @MapsId("user2")
    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    @Column(name = "is_accepted", nullable = false)
    private Boolean isAccepted;

    public Matching accept(boolean b) {
        this.isAccepted = b;
        return this;
    }
}