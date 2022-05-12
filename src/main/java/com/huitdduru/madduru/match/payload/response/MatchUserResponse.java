package com.huitdduru.madduru.match.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchUserResponse {

    private String user1;
    private String user2;
    private String userImage1;
    private String userImage2;

}
