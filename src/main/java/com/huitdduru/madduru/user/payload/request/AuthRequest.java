package com.huitdduru.madduru.user.payload.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthRequest {

    private final String email;
    private final String password;

}
