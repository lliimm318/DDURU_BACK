package com.huitdduru.madduru.diary.service;

import com.huitdduru.madduru.diary.payload.request.DiaryRequest;
import com.huitdduru.madduru.diary.payload.response.ChronologyResponse;
import com.huitdduru.madduru.diary.payload.response.DiaryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DiaryService {

    void writeDiary(int diaryId, MultipartFile file, DiaryRequest diaryRequest) throws IOException;

    List<ChronologyResponse> choronology();

    List<DiaryResponse> diaryList(int diaryId);

}
