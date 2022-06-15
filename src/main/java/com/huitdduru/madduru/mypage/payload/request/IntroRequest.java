package com.huitdduru.madduru.mypage.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class IntroRequest {
    @NotNull
    private String intro;
}

