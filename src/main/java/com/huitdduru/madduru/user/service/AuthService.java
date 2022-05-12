package com.huitdduru.madduru.user.service;

import com.huitdduru.madduru.user.payload.request.AuthRequest;
import com.huitdduru.madduru.user.payload.request.RegisterRequest;
import com.huitdduru.madduru.user.payload.response.ImageResponse;
import com.huitdduru.madduru.user.payload.response.TokenResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthService {

    void register(RegisterRequest registerRequest);

    TokenResponse auth(AuthRequest authRequest);

    TokenResponse refreshToken(String token);

    ImageResponse uploadImage(MultipartFile file) throws IOException;

}
