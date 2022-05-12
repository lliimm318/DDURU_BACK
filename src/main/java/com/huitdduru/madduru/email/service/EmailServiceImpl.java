package com.huitdduru.madduru.email.service;

import com.huitdduru.madduru.email.entity.RandomCode;
import com.huitdduru.madduru.email.repository.RandomCodeRepository;
import com.huitdduru.madduru.exception.exceptions.NotEmailFormatException;
import com.huitdduru.madduru.exception.exceptions.VerifyNumNotFoundException;
import com.huitdduru.madduru.email.payload.request.MailRequest;
import com.huitdduru.madduru.email.payload.request.RandomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final static Random RANDOM = new Random();
    private final JavaMailSender javaMailSender;

    private final RandomCodeRepository randomCodeRepository;

    @Transactional
    @Override
    public void sendMail(MailRequest mailRequest) {
       String randomCode = generateRandomCode();

        if(!isValidEmailAddress(mailRequest.getEmail())) {
            throw new NotEmailFormatException();
        }

        try {
           final MimeMessagePreparator preparator = mimeMessage -> {
               final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
               helper.setFrom("huitddu@gmail.com");
               helper.setTo(mailRequest.getEmail());
               helper.setText("휘뚜루마뚜루 인증 번호는 " + randomCode + "입니다");
               helper.setSubject("휘뚜루마뚜루 인증 번호");
           };

           javaMailSender.send(preparator);

           RandomCode random = RandomCode.builder()
                   .email(mailRequest.getEmail())
                   .isVerified(false)
                   .build();

           random.updateCode(randomCode);
           randomCodeRepository.save(random);
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    @Override
    public void randomCode(RandomRequest randomRequest) {
        RandomCode randomCode = randomCodeRepository.findByEmail(randomRequest.getEmail())
                .orElseThrow(VerifyNumNotFoundException::new);

        if(!randomCode.getRandomCode().equals(randomRequest.getCode())) {
            throw new VerifyNumNotFoundException();
        }

        randomCode.updateVerified();
        randomCodeRepository.save(randomCode);
    }

    private String generateRandomCode() {
        RANDOM.setSeed(System.currentTimeMillis());
        return Integer.toString(RANDOM.nextInt(1000000));
    }

    private boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

}
