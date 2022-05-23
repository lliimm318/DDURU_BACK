package com.huitdduru.madduru.diary.service;

import com.huitdduru.madduru.diary.entity.DiaryDetail;
import com.huitdduru.madduru.diary.payload.response.CalendarCountResponse;
import com.huitdduru.madduru.diary.payload.response.CalendarResponse;
import com.huitdduru.madduru.diary.repository.DiaryDetailRepository;
import com.huitdduru.madduru.security.auth.AuthenticationFacade;
import com.huitdduru.madduru.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final DiaryDetailRepository diaryDetailRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<CalendarCountResponse> calendarCount(int year, int month) {
        String monthString = month < 10 ? "0" + month : month + "";
        String date = year + "-" + monthString;

        List<DiaryDetail> details = diaryDetailRepository.findByDateContainsOrderByDate(date);
        List<CalendarCountResponse> calendarList = new ArrayList<>();

        Map<String, Integer> calMap = new LinkedHashMap<>();

        List<String> calendar = calendar(year, month);
        for (String s : calendar) {
            calMap.put(s, 0);
        }

        for (DiaryDetail d : details) {
            calMap.put(d.getDate(), calMap.getOrDefault(d.getDate(), 0)+1);
        }

        for (String key : calMap.keySet()) {
            CalendarCountResponse calendarCountResponse = new CalendarCountResponse();
            calendarCountResponse.setDate(key);
            calendarCountResponse.setCount(calMap.get(key));

            calendarList.add(calendarCountResponse);
        }

        return calendarList;
    }

    @Override
    public List<CalendarResponse> diaryCalendar(String date) {
        List<DiaryDetail> details = diaryDetailRepository.findByDateOrderByDate(date);
        List<CalendarResponse> calendarResponses = new ArrayList<>();

        User user = authenticationFacade.getUser();

        for (DiaryDetail d : details) {
            Boolean isMine = user == d.getUser();

            CalendarResponse response = CalendarResponse.builder()
                    .id(d.getId())
                    .title(d.getTitle())
                    .date(d.getDate())
                    .writer(d.getUser().getName())
                    .imageUrl(d.getImagePath())
                    .isMine(isMine)
                    .build();

            calendarResponses.add(response);
        }
        return calendarResponses;
    }

    private List<String> calendar(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);

        List<String> cal = new ArrayList<>();

        String monthString = month < 10 ? "0" + month : month + "";
        int max = calendar.getActualMaximum(Calendar.DATE);

        for (int i = 1; i <= max+1; i++) {
            String date = year + "-" + monthString;
            if (i < 10) date += "-" + 0 + i;
            else date += "-" + i;

            cal.add(date);
        }
        return cal;
    }
}
