package com.huitdduru.madduru.diary.service;

import com.huitdduru.madduru.diary.payload.request.DiaryRequest;
import com.huitdduru.madduru.diary.payload.response.DetailListResponse;
import com.huitdduru.madduru.diary.payload.response.DiaryDetailResponse;
import com.huitdduru.madduru.diary.payload.response.DiaryListResponse;

import java.util.List;

public interface DiaryService {

    void writeDiary(int diaryId, DiaryRequest diaryRequest);

    List<DiaryListResponse> chronology();

    DetailListResponse diaryList(int diaryId);

    DiaryDetailResponse diaryDetail(int diaryId);

}
