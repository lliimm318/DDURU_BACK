package com.huitdduru.madduru.user.service;

import com.huitdduru.madduru.user.domain.User;
import com.huitdduru.madduru.user.domain.repository.UserRepository;
import com.huitdduru.madduru.user.payload.request.MailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(MailRequest mailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();

        Random random = new Random();
        String randomCode = String.valueOf(random.nextInt());

        message.setTo(mailRequest.getEmail());
        message.setSubject("휘뚜루마뚜루 인증 코드입니다.");
        message.setText(randomCode);
        javaMailSender.send((message));
    }

}
