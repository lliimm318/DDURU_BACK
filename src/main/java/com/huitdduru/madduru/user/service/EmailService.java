package com.huitdduru.madduru.user.service;

import com.huitdduru.madduru.user.payload.request.MailRequest;
import com.huitdduru.madduru.user.payload.request.RandomRequest;

public interface EmailService {

    void sendMail(MailRequest mailRequest);

    void randomCode(RandomRequest randomRequest);

}
