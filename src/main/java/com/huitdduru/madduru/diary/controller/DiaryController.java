package com.huitdduru.madduru.diary.controller;

import com.huitdduru.madduru.diary.payload.request.DiaryRequest;
import com.huitdduru.madduru.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public void writeDiary(@PathVariable int diaryId,
                           @RequestBody DiaryRequest diaryRequest) throws IOException {
        diaryService.writeDiary(diaryId, diaryRequest);
    }

}
