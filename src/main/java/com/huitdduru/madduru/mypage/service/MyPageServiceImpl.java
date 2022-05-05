package com.huitdduru.madduru.mypage.service;

import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.diary.entity.DiaryDetail;
import com.huitdduru.madduru.diary.repository.DiaryDetailRepository;
import com.huitdduru.madduru.diary.repository.DiaryRepository;
import com.huitdduru.madduru.mypage.payload.request.IntroRequest;
import com.huitdduru.madduru.diary.payload.response.DiaryResponse;
import com.huitdduru.madduru.mypage.payload.response.MyInfoResponse;
import com.huitdduru.madduru.s3.FileUploader;
import com.huitdduru.madduru.security.auth.AuthenticationFacade;
import com.huitdduru.madduru.user.entity.User;
import com.huitdduru.madduru.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final FileUploader fileUploader;
    private final DiaryRepository diaryRepository;
    private final DiaryDetailRepository diaryDetailRepository;

    @Override
    public void updateIntroduction(IntroRequest introRequest) {
        User user = authenticationFacade.getUser().setIntro(introRequest.getIntro());
        userRepository.save(user);
    }

    @Override
    public void updateProfileImage(MultipartFile image) throws IOException {
        User user = authenticationFacade.getUser();

        String imagePath = image != null ? fileUploader.uploadFile(image) : null;

        if(user.getImagePath() != null)
            fileUploader.removeFile(user.getImagePath());

        userRepository.save(user.setImagePath(imagePath));
    }

    @Override
    public void unregister() {
        userRepository.save(authenticationFacade.getUser().unregister());
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

                    boolean currentUserIsUser1 = user1.equals(user);

                    User currentUser = currentUserIsUser1 ? user1 : user2,
                            mate = !currentUserIsUser1 ? user1 : user2;

                    DiaryDetail mostRecentDiaryDetail = diaryDetailRepository.findFirstByDiaryOrderByCreatedAtDesc(diary);

                    return DiaryResponse.builder()
                        .diaryId(diary.getId())
                        .currentUserImg(currentUser.getImagePath())
                        .mateImg(mate.getImagePath())
                        .isMyTurn(mostRecentDiaryDetail != null ?
                                mostRecentDiaryDetail.getUser().equals(mate) : null)
                        .mateName(mate.getName())
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
}
