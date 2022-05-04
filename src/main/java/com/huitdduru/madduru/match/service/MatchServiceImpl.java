package com.huitdduru.madduru.match.service;

import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.diary.repository.DiaryRepository;
import com.huitdduru.madduru.exception.exceptions.UserNotFoundException;
import com.huitdduru.madduru.match.payload.response.MatchUserResponse;
import com.huitdduru.madduru.s3.FileUploader;
import com.huitdduru.madduru.security.auth.AuthenticationFacade;
import com.huitdduru.madduru.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final DiaryRepository diaryRepository;

    private final FileUploader fileUploader;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public MatchUserResponse matchResponse() {
        User user = authenticationFacade.getUser();
        Diary diary = diaryRepository.findByUser1OrUser2AndRelationContinuesIsTrue(user)
                .orElseThrow(UserNotFoundException::new);

        String image1 = fileUploader.getUrl(diary.getUser1().getImagePath());
        String image2 = fileUploader.getUrl(diary.getUser2().getImagePath());

        return MatchUserResponse.builder()
                .user1(diary.getUser1().getName())
                .user2(diary.getUser2().getName())
                .userImage1(image1)
                .userImage2(image2)
                .build();
    }

    @Override
    public void matchEnd() {
        User user = authenticationFacade.getUser();
        Diary diary = diaryRepository.findByUser1OrUser2AndRelationContinuesIsTrue(user)
                .orElseThrow(UserNotFoundException::new);

        diaryRepository.save(diary.updateRelation(false));
    }
}
