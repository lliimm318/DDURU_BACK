package com.huitdduru.madduru.email.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@RedisHash(value = "random_code", timeToLive = 60 * 3)
@NoArgsConstructor
@AllArgsConstructor
public class RandomCode {

    @Id
    private String email;

    private String randomCode;

    private boolean isVerified;

    public RandomCode isVerifiedTrue() {
        this.isVerified = true;

        return this;
    }

}
