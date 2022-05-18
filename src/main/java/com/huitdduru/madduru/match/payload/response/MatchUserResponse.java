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

    private String me;
    private String opponent;
    private String myImage;
    private String opponentImage;

}
