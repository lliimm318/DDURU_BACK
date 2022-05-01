package com.huitdduru.madduru.diary.controller;

import com.huitdduru.madduru.diary.payload.request.DiaryRequest;
import com.huitdduru.madduru.diary.payload.response.ChronologyResponse;
import com.huitdduru.madduru.diary.payload.response.DiaryDetailResponse;
import com.huitdduru.madduru.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/{diaryId}")
    public void writeDiary(@PathVariable int diaryId,
                           @RequestPart(required = false) MultipartFile file,
                           @RequestPart DiaryRequest diaryRequest) throws IOException {
        diaryService.writeDiary(diaryId, file, diaryRequest);
    }

    @GetMapping("/chronology")
    public List<ChronologyResponse> getChronology() {
        return diaryService.choronology();
    }

    @GetMapping("/{diaryId}/list")
    public List<DiaryDetailResponse> getDiaryList(@PathVariable int diaryId) {
        return diaryService.diaryList(diaryId);
    }

}
