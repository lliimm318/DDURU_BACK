package com.huitdduru.madduru.user.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthResponse {

    private final Integer diaryId;

    private final String accessToken;

    private final String refreshToken;

}
