package com.huitdduru.madduru.diary.service;

import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.diary.entity.DiaryDetail;
import com.huitdduru.madduru.diary.payload.request.DiaryRequest;
import com.huitdduru.madduru.diary.repository.DiaryDetailRepository;
import com.huitdduru.madduru.diary.repository.DiaryRepository;
import com.huitdduru.madduru.exception.exceptions.DiaryNotFoundException;
import com.huitdduru.madduru.s3.FileUploader;
import com.huitdduru.madduru.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final DiaryDetailRepository diaryDetailRepository;

    private final AuthenticationFacade authenticationFacade;
    private final FileUploader fileUploader;

    @Override
    public void writeDiary(int diaryId, DiaryRequest diaryRequest) throws IOException {

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(DiaryNotFoundException::new);

        String imagePath = UUID.randomUUID().toString();
        fileUploader.uploadFile(diaryRequest.getImage(), imagePath);

        DiaryDetail diaryDetail = DiaryDetail.builder()
                .title(diaryRequest.getTitle())
                .content(diaryRequest.getContents())
                .user(authenticationFacade.getUser())
                .date(diaryRequest.getDate())
                .createdAt(LocalDateTime.now())
                .feeling(diaryRequest.getFeeling())
                .image_path(imagePath)
                .diary(diary)
                .build();

        diaryDetailRepository.save(diaryDetail);
    }
}
