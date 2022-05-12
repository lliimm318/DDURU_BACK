package com.huitdduru.madduru.mypage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.diary.repository.DiaryRepository;
import com.huitdduru.madduru.exception.exceptions.UserNotAccessExcepion;
import com.huitdduru.madduru.mypage.payload.request.IntroRequest;
import com.huitdduru.madduru.security.TokenProvider;
import com.huitdduru.madduru.user.entity.User;
import com.huitdduru.madduru.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MyPageControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;
    @Autowired
    DiaryRepository diaryRepository;

    @Autowired
    TokenProvider tokenProvider;

    User user;

    private String token;

    protected void setToken(String token) {
        this.token = token;
    }

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
    void queryDiaryList() throws Exception {
        setToken(tokenProvider.generateAccessToken(user.getEmail()));

        User mate = userRepository.save(User.builder()
                .name("d.mate")
                .email("d@d.com")
                .intro("Im your mate")
                .password("pass2")
                .code("ABCDEF")
                .isExist(true)
                .build());

        // 일기장 저장
        saveDiary(mate);

        //나만의 일기
        diaryRepository.save(Diary.builder()
                .user1(user)
                .user2(user)
                .createdAt(LocalDateTime.now())
                .finishedAt(LocalDateTime.now())
                .relationContinues(true)
                .build());

        saveDiary(mate);


        //나만의 일기가 제일 처음에 오는지 검증
        mvc.perform(MockMvcRequestBuilders.get("/diary-list")
                .header("token", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mateName").value(user.getName()))
                .andExpect(jsonPath("$[10]").exists())
                .andExpect(jsonPath("$[11]").doesNotExist());
    }

    private void saveDiary(User mate) {
        for (int i = 0; i < 5; i++) {
            diaryRepository.save(Diary.builder()
                    .user1(user)
                    .user2(mate)
                    .createdAt(LocalDateTime.now())
                    .finishedAt(LocalDateTime.now())
                    .relationContinues(true)
                    .build());
        }
    }

    @Test
    void queryMyInfo() {
    }
}