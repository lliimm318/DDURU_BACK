package com.huitdduru.madduru.user.service;

import com.huitdduru.madduru.exception.exceptions.InvalidTokenException;
import com.huitdduru.madduru.exception.exceptions.PasswordNotMatchException;
import com.huitdduru.madduru.exception.exceptions.UserAlreadyException;
import com.huitdduru.madduru.exception.exceptions.UserNotFoundException;
import com.huitdduru.madduru.s3.FileUploader;
import com.huitdduru.madduru.security.TokenProvider;
import com.huitdduru.madduru.redis.RefreshToken;
import com.huitdduru.madduru.user.domain.User;
import com.huitdduru.madduru.user.domain.repository.RefreshTokenRepository;
import com.huitdduru.madduru.user.domain.repository.UserRepository;
import com.huitdduru.madduru.user.payload.request.AuthRequest;
import com.huitdduru.madduru.user.payload.request.RegisterRequest;
import com.huitdduru.madduru.user.payload.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    public final RefreshTokenRepository refreshTokenRepository;

    private final FileUploader fileUploader;

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Value("${auth.jwt.exp.refresh}")
    private Long ttl;

    @Override
    public void register(RegisterRequest registerRequest) throws IOException {
        userRepository.findByEmail(registerRequest.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyException();
                });

        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .isExist(true)
                .imageName(registerRequest.getEmail())
                .build();

        String imageName = user.getEmail();
        fileUploader.uploadFile(registerRequest.getFile(), imageName);

        userRepository.save(user);
    }

    @Override
    public TokenResponse auth(AuthRequest authRequest) {
        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException();
        }

        RefreshToken refreshToken = RefreshToken.builder()
                .email(authRequest.getEmail())
                .refreshToken(tokenProvider.generateRefreshToken(authRequest.getEmail()))
                .ttl(ttl)
                .build();

        refreshTokenRepository.save(refreshToken);

        return TokenResponse.builder()
                .accessToken(tokenProvider.generateAccessToken(authRequest.getEmail()))
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    @Override
    public TokenResponse refreshToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token)
                .map(refreshToken -> {
                    String generatedAccessToken = tokenProvider.generateAccessToken(refreshToken.getEmail());
                    return refreshToken.update(generatedAccessToken, ttl);
                })
                .map(refreshTokenRepository::save)
                .map(refreshToken -> {
                    String generatedAccessToken = tokenProvider.generateAccessToken(refreshToken.getEmail());
                    return new TokenResponse(generatedAccessToken, refreshToken.getRefreshToken());
                })
                .orElseThrow(InvalidTokenException::new);
    }
}
