package com.huitdduru.madduru.user.service;

import com.huitdduru.madduru.user.payload.request.MailRequest;

public interface EmailService {

    void sendMail(MailRequest mailRequest);

}
