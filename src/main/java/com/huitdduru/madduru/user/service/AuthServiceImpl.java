package com.huitdduru.madduru.user.service;

import com.huitdduru.madduru.email.entity.RandomCode;
import com.huitdduru.madduru.exception.exceptions.*;
import com.huitdduru.madduru.email.repository.RandomCodeRepository;
import com.huitdduru.madduru.s3.FileUploader;
import com.huitdduru.madduru.security.TokenProvider;
import com.huitdduru.madduru.redis.RefreshToken;
import com.huitdduru.madduru.user.entity.User;
import com.huitdduru.madduru.redis.RefreshTokenRepository;
import com.huitdduru.madduru.user.payload.response.ImageResponse;
import com.huitdduru.madduru.user.repository.UserRepository;
import com.huitdduru.madduru.user.payload.request.AuthRequest;
import com.huitdduru.madduru.user.payload.request.RegisterRequest;
import com.huitdduru.madduru.user.payload.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RandomCodeRepository randomCodeRepository;

    private final FileUploader fileUploader;

    private final static Random RANDOM = new Random();
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Value("${auth.jwt.exp.refresh}")
    private Long ttl;

    @Override
    public void register(RegisterRequest registerRequest) {
        randomCodeRepository.findByEmail(registerRequest.getEmail())
                .filter(RandomCode::isVerified)
                .orElseThrow(UserNotAccessExcepion::new);

        userRepository.findByEmail(registerRequest.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyException();
                });

        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .isExist(true)
                .intro(registerRequest.getIntro())
                .code(generateRandomCode())
                .imagePath(registerRequest.getImageUrl())
                .build();

        userRepository.save(user);
    }

    @Override
    public TokenResponse auth(AuthRequest authRequest) {
        User user = userRepository.findByEmail(authRequest.getEmail())
                .filter(u -> passwordEncoder.matches(authRequest.getPassword(), u.getPassword()))
                .orElseThrow(UserNotFoundException::new);

        RefreshToken refreshToken = RefreshToken.builder()
                .email(user.getEmail())
                .refreshToken(tokenProvider.generateRefreshToken(user.getEmail()))
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
                    String generatedAccessToken = tokenProvider.generateRefreshToken(refreshToken.getEmail());
                    return refreshToken.update(generatedAccessToken, ttl);
                })
                .map(refreshTokenRepository::save)
                .map(refreshToken -> {
                    String generatedAccessToken = tokenProvider.generateAccessToken(refreshToken.getEmail());
                    return new TokenResponse(generatedAccessToken, refreshToken.getRefreshToken());
                })
                .orElseThrow(InvalidTokenException::new);
    }

    @Override
    public ImageResponse uploadImage(MultipartFile file) throws IOException {
        return ImageResponse.builder()
                .imageUrl(fileUploader.uploadFile(file))
                .build();
    }

    private String generateRandomCode() {
        RANDOM.setSeed(System.currentTimeMillis());
        return Integer.toString(RANDOM.nextInt(1000000));
    }
}
