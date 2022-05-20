package com.huitdduru.madduru.diary.service;

import com.huitdduru.madduru.diary.payload.request.DiaryRequest;
import com.huitdduru.madduru.diary.payload.response.CalendarResponse;
import com.huitdduru.madduru.diary.payload.response.DetailListResponse;
import com.huitdduru.madduru.diary.payload.response.DiaryListResponse;

import java.util.List;

public interface DiaryService {

    void writeDiary(int diaryId, DiaryRequest diaryRequest);

    List<DiaryListResponse> choronology();

    DetailListResponse diaryList(int diaryId);

    List<CalendarResponse> diaryCalendar(int year, int month);

}
