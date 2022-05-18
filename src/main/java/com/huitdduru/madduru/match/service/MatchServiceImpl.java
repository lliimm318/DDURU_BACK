package com.huitdduru.madduru.match.service;

import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.diary.repository.DiaryRepository;
import com.huitdduru.madduru.exception.exceptions.UserNotFoundException;
import com.huitdduru.madduru.exception.exceptions.UserNotMatchException;
import com.huitdduru.madduru.match.payload.response.MatchUserResponse;
import com.huitdduru.madduru.security.auth.AuthenticationFacade;
import com.huitdduru.madduru.user.entity.User;
import com.huitdduru.madduru.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public MatchUserResponse matchResponse() {
        User user = authenticationFacade.getUser();
        Diary diary = diaryRepository.findByUser1OrUser2AndRelationContinuesIsTrue(user)
                .orElseThrow(UserNotMatchException::new);

        User opponent = diary.getUser1() == user ? diary.getUser2() : diary.getUser1();

        System.out.println(opponent.getName());

        return MatchUserResponse.builder()
                .me(user.getName())
                .myImage(user.getImagePath())
                .opponent(opponent.getName())
                .opponentImage(opponent.getImagePath())
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
