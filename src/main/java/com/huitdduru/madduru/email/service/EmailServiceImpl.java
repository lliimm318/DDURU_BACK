package com.huitdduru.madduru.email.service;

import com.huitdduru.madduru.exception.exceptions.MailSendException;
import com.huitdduru.madduru.email.entity.RandomCode;
import com.huitdduru.madduru.email.repository.RandomCodeRepository;
import com.huitdduru.madduru.exception.exceptions.VerifyNumNotFoundException;
import com.huitdduru.madduru.email.payload.request.MailRequest;
import com.huitdduru.madduru.email.payload.request.RandomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

       try {
           final MimeMessagePreparator preparator = mimeMessage -> {
               final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
               helper.setFrom("huitddu@gmail.com");
               helper.setTo(mailRequest.getEmail());
               helper.setSubject("휘뚜루마뚜루 인종 번호는 " + randomCode + "입니다");
               helper.setText("휘뚜루마뚜루");
           };
           javaMailSender.send(preparator);

           randomCodeRepository.findByEmail(mailRequest.getEmail())
                   .ifPresent(randomCodeRepository::delete);

           RandomCode random = RandomCode.builder()
                   .email(mailRequest.getEmail())
                   .randomCode(randomCode)
                   .build();

           randomCodeRepository.save(random);
       } catch (Exception e) {
           e.printStackTrace();
           throw new MailSendException();
       }
    }

    @Override
    public void randomCode(RandomRequest randomRequest) {
        randomCodeRepository.findByEmail(randomRequest.getEmail())
                .filter(randomCode -> randomCode.getRandomCode().equals(randomRequest.getCode()))
                .map(RandomCode::isVerifiedTrue)
                .map(randomCodeRepository::save)
                .orElseThrow(VerifyNumNotFoundException::new);
    }

    private String generateRandomCode() {
        RANDOM.setSeed(System.currentTimeMillis());

        return Integer.toString(RANDOM.nextInt(1000000) % 1000000);
    }

}
