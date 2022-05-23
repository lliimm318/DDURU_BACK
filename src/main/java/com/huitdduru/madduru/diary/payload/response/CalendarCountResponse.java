package com.huitdduru.madduru.diary.payload.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarCountResponse {

    private String date;

    private Integer count;

}
