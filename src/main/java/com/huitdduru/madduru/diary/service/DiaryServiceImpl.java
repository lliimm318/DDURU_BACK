package com.huitdduru.madduru.diary.service;

import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.diary.entity.DiaryDetail;
import com.huitdduru.madduru.diary.payload.request.DiaryRequest;
import com.huitdduru.madduru.diary.payload.response.DiaryListResponse;
import com.huitdduru.madduru.diary.payload.response.DiaryDetailResponse;
import com.huitdduru.madduru.diary.payload.response.ExchangeDiaryResponse;
import com.huitdduru.madduru.diary.repository.DiaryDetailRepository;
import com.huitdduru.madduru.diary.repository.DiaryRepository;
import com.huitdduru.madduru.exception.exceptions.DiaryNotFoundException;
import com.huitdduru.madduru.s3.FileUploader;
import com.huitdduru.madduru.security.auth.AuthenticationFacade;
import com.huitdduru.madduru.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final DiaryDetailRepository diaryDetailRepository;

    private final AuthenticationFacade authenticationFacade;
    private final FileUploader fileUploader;

    @Override
    public void writeDiary(int diaryId, DiaryRequest diaryRequest) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(DiaryNotFoundException::new);

        DiaryDetail diaryDetail = DiaryDetail.builder()
                .title(diaryRequest.getTitle())
                .content(diaryRequest.getContents())
                .user(authenticationFacade.getUser())
                .date(diaryRequest.getDate())
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
    public List<DiaryDetailResponse> diaryList(int diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(DiaryNotFoundException::new);

        return diaryDetailRepository.findByDiaryOrderByCreatedAt(diary).stream()
                .map(diaryDetail -> DiaryDetailResponse.builder()
                        .id(diaryDetail.getId())
                        .writer(diaryDetail.getUser().getName())
                        .title(diaryDetail.getTitle())
                        .date(diaryDetail.getDate())
                        .content(diaryDetail.getContent())
                        .feeling(diaryDetail.getFeeling())
                        .image(diaryDetail.getImagePath())
                        .build())
                .collect(Collectors.toList());
    }
}
