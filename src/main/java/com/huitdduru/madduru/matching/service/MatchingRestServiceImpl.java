package com.huitdduru.madduru.matching.service;

import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.diary.repository.DiaryRepository;
import com.huitdduru.madduru.exception.exceptions.AlreadyRelationContinuesException;
import com.huitdduru.madduru.exception.exceptions.UserNotFoundException;
import com.huitdduru.madduru.matching.payload.FriendCodeRequest;
import com.huitdduru.madduru.security.auth.AuthenticationFacade;
import com.huitdduru.madduru.user.entity.User;
import com.huitdduru.madduru.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MatchingRestServiceImpl implements MatchingRestService {

    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    @Override
    public void friendMatching(FriendCodeRequest request) {
        String code = request.getCode();

        User user = authenticationFacade.getUser();

        Optional<Diary> diary = diaryRepository.findByUser1OrUser2AndRelationContinuesIsTrue(user);

        if (diary.isPresent() && diary.get().isRelationContinues())
            throw new AlreadyRelationContinuesException();

        User mate = userRepository.findByCode(code).orElseThrow(UserNotFoundException::new);

        boolean cond = user.getId() < mate.getId();

        diaryRepository.save(Diary.builder()
                .user1(cond ? user : mate)
                .user2(cond ? mate : user)
                .createdAt(LocalDateTime.now())
                .relationContinues(true)
                .build());
    }
}
