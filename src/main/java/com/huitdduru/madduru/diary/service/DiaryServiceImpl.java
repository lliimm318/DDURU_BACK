package com.huitdduru.madduru.diary.service;

import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.diary.entity.DiaryDetail;
import com.huitdduru.madduru.diary.payload.request.DiaryRequest;
import com.huitdduru.madduru.diary.payload.response.*;
import com.huitdduru.madduru.diary.repository.DiaryDetailRepository;
import com.huitdduru.madduru.diary.repository.DiaryRepository;
import com.huitdduru.madduru.exception.exceptions.DiaryNotFoundException;
import com.huitdduru.madduru.security.auth.AuthenticationFacade;
import com.huitdduru.madduru.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final DiaryDetailRepository diaryDetailRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public void writeDiary(int diaryId, DiaryRequest diaryRequest) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(DiaryNotFoundException::new);

        DiaryDetail diaryDetail = DiaryDetail.builder()
                .title(diaryRequest.getTitle())
                .content(diaryRequest.getContents())
                .user(authenticationFacade.getUser())
                .date(diaryRequest.getDate().toString())
                .createdAt(LocalDateTime.now())
                .feeling(diaryRequest.getFeeling())
                .imagePath(diaryRequest.getImageUrl())
                .diary(diary)
                .build();

        diaryDetailRepository.save(diaryDetail);
    }

    @Override
    public List<DiaryListResponse> choronology() {
        User user = authenticationFacade.getUser();

        List<Diary> diaryList = diaryRepository.findByUser1OrUser2(user);
        diaryList.add(diaryRepository.findByUser1AndUser2(user, user));
        List<DiaryListResponse> responses = new ArrayList<>();

        for(Diary diary: diaryList) {
            List<DiaryDetail> details = diaryDetailRepository.findByDiaryOrderByCreatedAt(diary);

            for (DiaryDetail d : details) {
                ExchangeDiaryResponse exchangeDiaryResponse = ExchangeDiaryResponse.builder()
                        .id(d.getId())
                        .title(d.getTitle())
                        .writer(d.getUser().getName())
                        .date(d.getDate())
                        .createdAt(d.getCreatedAt())
                        .build();

                DiaryListResponse diaryListResponse = new DiaryListResponse();
                diaryListResponse.setId(d.getId());
                diaryListResponse.setDiary(exchangeDiaryResponse);
                diaryListResponse.setIsMine(false);
                if (user==d.getUser()) diaryListResponse.setIsMine(true);

                responses.add(diaryListResponse);
            }
        }
        return responses;
    }

    @Override
    public DetailListResponse diaryList(int diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(DiaryNotFoundException::new);

        User user = authenticationFacade.getUser();
        User opponent = diary.getUser1() == user ? diary.getUser2() : diary.getUser1();

        List<DiaryDetail> diaryDetail = diaryDetailRepository.findByDiaryOrderByCreatedAt(diary);

        List<DetailResponse> responses = new ArrayList<>();
        DetailListResponse detailListResponse = new DetailListResponse();
        detailListResponse.setMatchedUserName(opponent.getName());

        for (DiaryDetail d : diaryDetail) {
            DetailResponse detailResponse = DetailResponse.builder()
                    .id(d.getId())
                    .title(d.getTitle())
                    .date(d.getDate())
                    .content(d.getContent())
                    .feeling(d.getFeeling())
                    .image(d.getImagePath())
                    .writer(d.getUser().getName())
                    .userImage(d.getUser().getImagePath())
                    .build();

            responses.add(detailResponse);
        }
        detailListResponse.setList(responses);

        return detailListResponse;
    }

    @Override
    public DiaryDetailResponse diaryDetail(int diaryId) {
        DiaryDetail diaryDetail = diaryDetailRepository.findById(diaryId)
                .orElseThrow(DiaryNotFoundException::new);

        return DiaryDetailResponse.builder()
                .id(diaryDetail.getId())
                .writer(diaryDetail.getUser().getName())
                .title(diaryDetail.getTitle())
                .date(diaryDetail.getDate())
                .content(diaryDetail.getContent())
                .feeling(diaryDetail.getFeeling())
                .image(diaryDetail.getImagePath())
                .build();
    }
}
