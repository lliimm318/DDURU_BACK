package com.huitdduru.madduru.diary.controller;

import com.huitdduru.madduru.diary.payload.response.CalendarCountResponse;
import com.huitdduru.madduru.diary.payload.response.CalendarResponse;
import com.huitdduru.madduru.diary.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/{year}/{month}")
    public List<CalendarCountResponse> getCalendar(@PathVariable int year,
                                                   @PathVariable int month) {
        return calendarService.calendarCount(year, month);
    }

    @GetMapping("/{date}")
    public List<CalendarResponse> getCalendarDiaies(@PathVariable String date) {
        return calendarService.diaryCalendar(date);
    }

}
