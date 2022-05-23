package com.huitdduru.madduru.diary.service;

import com.huitdduru.madduru.diary.payload.response.CalendarCountResponse;
import com.huitdduru.madduru.diary.payload.response.CalendarResponse;

import java.util.List;

public interface CalendarService {

    List<CalendarCountResponse> calendarCount(int year, int month);

    List<CalendarResponse> diaryCalendar(String date);

}
