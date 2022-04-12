package com.huitdduru.madduru.email.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RandomCode {

    @Id
    private String email;

    private String randomCode;

    private boolean isVerified;

    public void updateVerified() {
        this.isVerified = true;
    }

    public RandomCode updateCode(String randomCode) {
        this.randomCode = randomCode;
        return this;
    }

}
