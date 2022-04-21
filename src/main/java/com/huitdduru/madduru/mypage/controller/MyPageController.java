package com.huitdduru.madduru.mypage.controller;

import com.huitdduru.madduru.mypage.payload.request.IntroRequest;
import com.huitdduru.madduru.diary.payload.response.DiaryResponse;
import com.huitdduru.madduru.mypage.payload.response.MyInfoResponse;
import com.huitdduru.madduru.mypage.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService mypageService;

    @PatchMapping("/intro")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateIntro(@RequestBody IntroRequest introRequest) {
        mypageService.updateIntroduction(introRequest);
    }

    @PatchMapping("/profile-image")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateProfileImage(@RequestPart(value = "image") MultipartFile image) throws IOException {
        mypageService.updateProfileImage(image);
    }

    @DeleteMapping("/unregister")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unregister() {
        mypageService.unregister();
    }

    @GetMapping("/diary-list")
    public List<DiaryResponse> queryDiaryList() {
        return mypageService.queryDiaryList();
    }

    @GetMapping("/my-info")
    public MyInfoResponse queryMyInfo() {
        return mypageService.queryMyInfo();
    }
}
