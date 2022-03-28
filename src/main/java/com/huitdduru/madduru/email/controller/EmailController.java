package com.huitdduru.madduru.email.controller;

import com.huitdduru.madduru.email.payload.request.MailRequest;
import com.huitdduru.madduru.email.payload.request.RandomRequest;
import com.huitdduru.madduru.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public void sendRandomCode(@RequestBody MailRequest mailRequest) {
        emailService.sendMail(mailRequest);
    }

    @PatchMapping
    public void random(@RequestBody RandomRequest randomRequest) {
        emailService.randomCode(randomRequest);
    }

}
