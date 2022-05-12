package com.huitdduru.madduru.diary.service;

import com.huitdduru.madduru.diary.payload.request.DiaryRequest;
import com.huitdduru.madduru.diary.payload.response.DiaryListResponse;
import com.huitdduru.madduru.diary.payload.response.DiaryDetailResponse;

import java.util.List;

public interface DiaryService {

    void writeDiary(int diaryId, DiaryRequest diaryRequest);

    List<DiaryListResponse> choronology();

    List<DiaryDetailResponse> diaryList(int diaryId);

}
