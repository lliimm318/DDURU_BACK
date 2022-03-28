package com.huitdduru.madduru.user.service;

import com.huitdduru.madduru.user.payload.request.AuthRequest;
import com.huitdduru.madduru.user.payload.request.RandomRequest;
import com.huitdduru.madduru.user.payload.request.RegisterRequest;
import com.huitdduru.madduru.user.payload.response.TokenResponse;

import java.io.IOException;

public interface AuthService {

    void register(RegisterRequest registerRequest) throws IOException;

    TokenResponse auth(AuthRequest authRequest);

    TokenResponse refreshToken(String token);

}
