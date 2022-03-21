package com.huitdduru.madduru.user.payload.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class RegisterRequest {

    private final String name;
    private final String email;
    private final String password;
    private final String info;
    private final MultipartFile file;

}
