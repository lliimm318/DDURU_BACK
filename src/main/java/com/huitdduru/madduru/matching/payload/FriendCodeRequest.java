package com.huitdduru.madduru.matching.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class FriendCodeRequest {
    @NotNull
    @Size(max = 6, min = 6)
    private String code;
}
