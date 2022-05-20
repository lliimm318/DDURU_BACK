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
        List<DiaryListResponse> responses = new ArrayList<>();
        Set<User> userList = new HashSet<>();

        for(Diary d: diaryList) {
            userList.add(d.getUser1());
            userList.add(d.getUser2());
        }

        for(User u : userList) {
            List<DiaryDetail> details = diaryDetailRepository.findByUserOrderByCreatedAt(u);

            for (DiaryDetail d : details) {
                ExchangeDiaryResponse exchangeDiaryResponse = ExchangeDiaryResponse.builder()
                        .id(d.getId())
                        .title(d.getTitle())
                        .writer(d.getUser().getName())
                        .date(d.getDate())
                        .createdAt(d.getCreatedAt())
                        .build();

                DiaryListResponse diaryListResponse = new DiaryListResponse();
                diaryListResponse.setId(u.getId());
                diaryListResponse.setDiary(exchangeDiaryResponse);
                diaryListResponse.setIsMine(false);
                if (user==u) diaryListResponse.setIsMine(true);

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

        List<DiaryDetailResponse> responses = new ArrayList<>();
        DetailListResponse detailListResponse = new DetailListResponse();
        detailListResponse.setMatchedUserName(opponent.getName());

        for (DiaryDetail d : diaryDetail) {
            DiaryDetailResponse detailResponse = DiaryDetailResponse.builder()
                    .id(d.getId())
                    .writer(d.getUser().getName())
                    .title(d.getTitle())
                    .date(d.getDate())
                    .content(d.getContent())
                    .feeling(d.getFeeling())
                    .image(d.getImagePath())
                    .build();

            responses.add(detailResponse);
        }
        detailListResponse.setList(responses);

        return detailListResponse;
    }

    @Override
    public List<CalendarResponse> diaryCalendar(int year, int month) {
        User user = authenticationFacade.getUser();
        String monthString = month < 10 ? "0" + month : month+"";

        List<CalendarResponse> calendarResponses = new ArrayList<>();

        List<DiaryDetail> diaries = diaryDetailRepository.findByDateContainsOrderByDate(year + "-"+ monthString);

        for (DiaryDetail d : diaries) {
            Boolean isMine = user == d.getUser();

            CalendarResponse response = CalendarResponse.builder()
                    .id(d.getId())
                    .title(d.getTitle())
                    .date(d.getDate())
                    .writer(d.getUser().getName())
                    .imageUrl(d.getImagePath())
                    .isMine(isMine)
                    .build();

            calendarResponses.add(response);
        }
        return calendarResponses;
    }
}
