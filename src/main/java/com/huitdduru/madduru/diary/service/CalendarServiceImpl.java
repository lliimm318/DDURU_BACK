package com.huitdduru.madduru.diary.service;

import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.diary.entity.DiaryDetail;
import com.huitdduru.madduru.diary.payload.response.CalendarCountResponse;
import com.huitdduru.madduru.diary.payload.response.CalendarResponse;
import com.huitdduru.madduru.diary.repository.DiaryDetailRepository;
import com.huitdduru.madduru.diary.repository.DiaryRepository;
import com.huitdduru.madduru.security.auth.AuthenticationFacade;
import com.huitdduru.madduru.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final DiaryDetailRepository diaryDetailRepository;
    private final DiaryRepository diaryRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<CalendarCountResponse> calendarCount(int year, int month) {
        String monthString = month < 10 ? "0" + month : month + "";
        String date = year + "-" + monthString;

        User user = authenticationFacade.getUser();

        List<Diary> diaryList = diaryList(user);
        List<DiaryDetail> detailList = new ArrayList<>();

        for (Diary d : diaryList) {
            detailList.addAll(diaryDetailRepository.findByDiaryAndDateContainsOrderByDate(d, date));
        }

        List<CalendarCountResponse> calendarList = new ArrayList<>();

        Map<String, Integer> calMap = new LinkedHashMap<>();

        List<String> calendar = calendar(year, month);
        for (String s : calendar) {
            calMap.put(s, 0);
        }

        for (DiaryDetail d : detailList) {
            calMap.put(date(d.getDate()), calMap.getOrDefault(date(d.getDate()), 0)+1);
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
        List<CalendarResponse> calendarResponses = new ArrayList<>();

        User user = authenticationFacade.getUser();

        List<Diary> diaryList = diaryList(user);
        List<DiaryDetail> detailList = new ArrayList<>();

        for (Diary d : diaryList) {
            detailList.addAll(diaryDetailRepository.findByDateOrderByDate(date));
        }

        for (DiaryDetail d : detailList) {
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
        int max = calendar.getActualMaximum(Calendar.DATE);

        for (int i = 1; i <= max+1; i++) {
            String date = year + "-" + month + "-" + i;
            cal.add(date);
        }
        return cal;
    }

    private String date(String date) {
        String[] d = date.split("-");

        if(d[1].charAt(0) == '0') d[1] = d[1].substring(1);
        if(d[2].charAt(0) == '0') d[2] = d[2].substring(1);

        return d[0] + "-" + d[1] + "-" + d[2];
    }

    private List<Diary> diaryList(User user) {
        List<Diary> diaryList = diaryRepository.findByUser1OrUser2(user);
        diaryList.add(diaryRepository.findByUser1AndUser2(user, user));

        return diaryList;
    }
}
