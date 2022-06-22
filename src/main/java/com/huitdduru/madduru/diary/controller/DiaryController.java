package com.huitdduru.madduru.diary.controller;

import com.huitdduru.madduru.diary.payload.request.DiaryRequest;
import com.huitdduru.madduru.diary.payload.response.DetailListResponse;
import com.huitdduru.madduru.diary.payload.response.DiaryDetailResponse;
import com.huitdduru.madduru.diary.payload.response.DiaryListResponse;
import com.huitdduru.madduru.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/{diaryId}")
    public void writeDiary(@PathVariable int diaryId,
                           @RequestBody DiaryRequest diaryRequest) {
        diaryService.writeDiary(diaryId, diaryRequest);
    }

    @GetMapping("/chronology")
    public List<DiaryListResponse> getChronology() {
        return diaryService.chronology();
    }

    @GetMapping("/{diaryId}/list")
    public DetailListResponse getDiaryList(@PathVariable int diaryId) {
        return diaryService.diaryList(diaryId);
    }

    @GetMapping("/{diaryDetailId}")
    public DiaryDetailResponse getDetail(@PathVariable int diaryDetailId) {
        return diaryService.diaryDetail(diaryDetailId);
    }

}
