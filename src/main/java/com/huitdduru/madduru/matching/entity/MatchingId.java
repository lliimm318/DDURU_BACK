package com.huitdduru.madduru.matching.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class MatchingId implements Serializable {

    private Integer user1;

    private Integer user2;
}
