package com.huitdduru.madduru.mypage.service;

import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.diary.entity.DiaryDetail;
import com.huitdduru.madduru.diary.payload.response.DiaryResponse;
import com.huitdduru.madduru.diary.repository.DiaryDetailRepository;
import com.huitdduru.madduru.diary.repository.DiaryRepository;
import com.huitdduru.madduru.mypage.payload.request.ImageUrlRequest;
import com.huitdduru.madduru.mypage.payload.request.IntroRequest;
import com.huitdduru.madduru.mypage.payload.request.MyInfoRequest;
import com.huitdduru.madduru.mypage.payload.response.CodeResponse;
import com.huitdduru.madduru.mypage.payload.response.MyInfoResponse;
import com.huitdduru.madduru.security.auth.AuthenticationFacade;
import com.huitdduru.madduru.user.entity.User;
import com.huitdduru.madduru.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final DiaryRepository diaryRepository;
    private final DiaryDetailRepository diaryDetailRepository;

    @Override
    public void updateInfo(MyInfoRequest request) {
        User user = authenticationFacade.getUser()
                .setMyInfos(request.getIntro(), request.getImageUrl());
        userRepository.save(user);
    }

    @Override
    public void unregister() {
        User user = authenticationFacade.getUser();
        List<Diary> diaries = diaryRepository.findByUser1OrUser2(user);
        diaries.forEach(diary -> diaryRepository.save(diary.updateRelation(false)));
        userRepository.deleteById(user.getId());
    }

    @Override
    public List<DiaryResponse> queryDiaryList() {
        User user = authenticationFacade.getUser();
        List<Diary> diaryList = diaryRepository.findByUser1OrUser2(user);
        Diary myOwnDiary = diaryRepository.findByUser1AndUser2(user, user);

        diaryList.add(0, myOwnDiary);   // 나만의 일기가 리스트의 제일 첫번째에 오게

        return diaryList.stream()
                .map(diary -> {
                    User user1 = diary.getUser1(), user2 = diary.getUser2();

                    boolean currentUserIsUser1 = user.equals(user1);

                    User currentUser = currentUserIsUser1 ? user1 : user2,
                            mate = !currentUserIsUser1 ? user1 : user2;

                    DiaryDetail mostRecentDiaryDetail = diaryDetailRepository.findFirstByDiaryOrderByCreatedAtDesc(diary);

                    return DiaryResponse.builder()
                        .diaryId(diary.getId())
                        .currentUserImg(currentUser.getImagePath())
                        .mateImg(mate != null ? mate.getImagePath() : null)
                        .isMyTurn(mostRecentDiaryDetail != null ?
                                mostRecentDiaryDetail.getUser().equals(mate) : null)
                        .mateName(mate != null ? mate.getName() : "탈퇴한 유저")
                        .hoursAgo(mostRecentDiaryDetail != null ?
                                ChronoUnit.HOURS.between(mostRecentDiaryDetail.getCreatedAt(), LocalDateTime.now()) : null)
                        .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public MyInfoResponse queryMyInfo() {
        User user = authenticationFacade.getUser();
        return MyInfoResponse.of(user);
    }

    @Override
    public CodeResponse code() {
        return new CodeResponse(authenticationFacade.getUser().getCode());
    }

    @Override
    public void updateIntroduction(IntroRequest request) {
        User user = authenticationFacade.getUser();
        userRepository.save(user.setIntro(request.getIntro()));
    }

    @Override
    public void updateProfileImage(ImageUrlRequest request) {
        User user = authenticationFacade.getUser();
        userRepository.save(user.setImageUrl(request.getImageUrl()));
    }
}
