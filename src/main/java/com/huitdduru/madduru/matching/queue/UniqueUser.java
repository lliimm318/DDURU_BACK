package com.huitdduru.madduru.matching.queue;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UniqueUser {

    private final String userId;
    private final UUID uuid;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof UniqueUser)) {
            return false;
        }

        UniqueUser u = (UniqueUser) obj;

        return this.userId.equals(u.getUserId());
    }
}
