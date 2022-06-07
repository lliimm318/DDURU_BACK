package com.huitdduru.madduru.matching.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class MatchingId implements Serializable {

    private Integer user1;

    private Integer user2;
}
