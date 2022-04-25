package com.huitdduru.madduru.match.service;

import com.huitdduru.madduru.match.payload.response.MatchUserResponse;

public interface MatchService {

    MatchUserResponse matchResponse();

    void matchEnd();

}
