package com.huitdduru.madduru.mypage.service;

import com.huitdduru.madduru.diary.payload.response.DiaryResponse;
import com.huitdduru.madduru.mypage.payload.request.ImageUrlRequest;
import com.huitdduru.madduru.mypage.payload.request.IntroRequest;
import com.huitdduru.madduru.mypage.payload.request.MyInfoRequest;
import com.huitdduru.madduru.mypage.payload.response.CodeResponse;
import com.huitdduru.madduru.mypage.payload.response.MyInfoResponse;

import java.util.List;

public interface MyPageService {

    void updateInfo(MyInfoRequest introRequest);

    void unregister();

    List<DiaryResponse> queryDiaryList();

    MyInfoResponse queryMyInfo();

    CodeResponse code();

    void updateIntroduction(IntroRequest request);

    void updateProfileImage(ImageUrlRequest request);
}
