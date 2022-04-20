package com.huitdduru.madduru.email.service;

import com.huitdduru.madduru.email.payload.request.MailRequest;
import com.huitdduru.madduru.email.payload.request.RandomRequest;

public interface EmailService {

    void sendMail(MailRequest mailRequest);

    void randomCode(RandomRequest randomRequest);

}
