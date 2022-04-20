package com.huitdduru.madduru.mypage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huitdduru.madduru.exception.exceptions.UserNotAccessExcepion;
import com.huitdduru.madduru.mypage.payload.request.IntroRequest;
import com.huitdduru.madduru.security.TokenProvider;
import com.huitdduru.madduru.user.entity.User;
import com.huitdduru.madduru.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MyPageControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenProvider tokenProvider;

    User user;

    @Setter(AccessLevel.PROTECTED)
    private String token;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.builder()
                .name("name")
                .email("email")
                .intro("intro")
                .password("pass")
                .code("SDFASD")
                .isExist(true)
                .build());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void updateIntro() throws Exception {
        setToken(tokenProvider.generateAccessToken(user.getEmail()));
        String updatedIntro = "updated intro";

        mvc.perform(MockMvcRequestBuilders.patch("/intro")
                .header("token", "Bearer " + token)
                .content(objectMapper.writeValueAsString(new IntroRequest(updatedIntro)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        User assertUser = userRepository.findByEmail(this.user.getEmail())
                .orElseThrow(UserNotAccessExcepion::new);

        assertEquals(assertUser.getIntro(), updatedIntro);
    }

    @Test
    void updateProfileImage() {
    }

    @Test
    void unregister() {
    }

    @Test
    void queryDiaryList() {
    }

    @Test
    void queryMyInfo() {
    }
}