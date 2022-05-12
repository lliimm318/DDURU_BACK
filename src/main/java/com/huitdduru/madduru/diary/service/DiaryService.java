package com.huitdduru.madduru.diary.service;

import com.huitdduru.madduru.diary.payload.request.DiaryRequest;
import com.huitdduru.madduru.diary.payload.response.DiaryListResponse;
import com.huitdduru.madduru.diary.payload.response.DiaryDetailResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DiaryService {

    void writeDiary(int diaryId, MultipartFile file, DiaryRequest diaryRequest) throws IOException;

    List<DiaryListResponse> choronology();

    List<DiaryDetailResponse> diaryList(int diaryId);

}
