package com.huitdduru.madduru.match.controller;

import com.huitdduru.madduru.match.payload.response.MatchUserResponse;
import com.huitdduru.madduru.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping("/match")
    public MatchUserResponse matchResponse() {
        return matchService.matchResponse();
    }

    @PutMapping("/match")
    public void endMatch() {
        matchService.matchEnd();
    }


}
