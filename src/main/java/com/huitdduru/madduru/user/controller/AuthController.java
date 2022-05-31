package com.huitdduru.madduru.user.controller;

import com.huitdduru.madduru.user.payload.request.AuthRequest;
import com.huitdduru.madduru.user.payload.request.RegisterRequest;
import com.huitdduru.madduru.user.payload.response.AuthResponse;
import com.huitdduru.madduru.user.payload.response.ImageResponse;
import com.huitdduru.madduru.user.payload.response.TokenResponse;
import com.huitdduru.madduru.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    void register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
    }

    @PostMapping("/auth")
    AuthResponse signIn(@RequestBody AuthRequest authRequest) {
        return authService.auth(authRequest);
    }

    @PutMapping("/auth")
    TokenResponse refresh(@RequestHeader("refresh-token") String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

    @PostMapping("/image")
    ImageResponse imageUpload(@ModelAttribute @Validated MultipartFile file) throws IOException {
        return authService.uploadImage(file);
    }

}
