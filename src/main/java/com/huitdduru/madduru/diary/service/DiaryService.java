package com.huitdduru.madduru.diary.service;

import com.huitdduru.madduru.diary.payload.request.DiaryRequest;

import java.io.IOException;

public interface DiaryService {

    void writeDiary(int diaryId, DiaryRequest diaryRequest) throws IOException;

}
