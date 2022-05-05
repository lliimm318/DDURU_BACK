package com.huitdduru.madduru.diary.service;

import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.diary.entity.DiaryDetail;
import com.huitdduru.madduru.diary.payload.request.DiaryRequest;
import com.huitdduru.madduru.diary.payload.response.ChronologyResponse;
import com.huitdduru.madduru.diary.payload.response.DiaryDetailResponse;
import com.huitdduru.madduru.diary.repository.DiaryDetailRepository;
import com.huitdduru.madduru.diary.repository.DiaryRepository;
import com.huitdduru.madduru.exception.exceptions.DiaryNotFoundException;
import com.huitdduru.madduru.s3.FileUploader;
import com.huitdduru.madduru.security.auth.AuthenticationFacade;
import com.huitdduru.madduru.user.entity.User;
import com.huitdduru.madduru.user.repository.UserRepository;
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
    public void writeDiary(int diaryId, MultipartFile file, DiaryRequest diaryRequest) throws IOException {

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(DiaryNotFoundException::new);

        String image = fileUploader.uploadFile(file);

        DiaryDetail diaryDetail = DiaryDetail.builder()
                .title(diaryRequest.getTitle())
                .content(diaryRequest.getContents())
                .user(authenticationFacade.getUser())
                .date(diaryRequest.getDate())
                .createdAt(LocalDateTime.now())
                .feeling(diaryRequest.getFeeling())
                .image_path(image)
                .diary(diary)
                 .build();

        diaryDetailRepository.save(diaryDetail);
    }

    @Override
    public List<ChronologyResponse> choronology() {
        User user = authenticationFacade.getUser();

        List<Diary> diaryList = diaryRepository.findByUser1OrUser2(user);
        List<ChronologyResponse> responses = new ArrayList<>();

        for(Diary d: diaryList) {
            User mate = !d.getUser1().equals(user) ? d.getUser1() : d.getUser2();

            ChronologyResponse chronologyResponse = new ChronologyResponse();
            chronologyResponse.setId(d.getId());
            chronologyResponse.setDiaries(new ArrayList<>());

            List<DiaryDetail> detailList = diaryDetailRepository.findByDiaryOrderByCreatedAt(d);

            for (DiaryDetail detail : detailList) {
                DiaryDetailResponse detailResponse = DiaryDetailResponse.builder()
                        .id(detail.getId())
                        .title(detail.getTitle())
                        .date(detail.getDate())
                        .writer(detail.getUser().getName())
                        .build();

                chronologyResponse.getDiaries().add(detailResponse);
            }
            responses.add(chronologyResponse);
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
                        .image(fileUploader.getUrl(diaryDetail.getImage_path()))
                        .build())
                .collect(Collectors.toList());
    }
}
