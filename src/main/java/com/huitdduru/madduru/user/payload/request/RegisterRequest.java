package com.huitdduru.madduru.user.payload.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterRequest {

    private final String name;
    private final String email;
    private final String password;
    private final String intro;

}
