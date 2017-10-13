package com.demo.service.Email;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Profile(value = {"testWithJavaMailSender"})
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmailServiceImpl implements EmailServiceSMM {

    private JavaMailSender mailSender;

    @Async
    @Override
    public void sendEmail(SimpleMailMessage email) {
      mailSender.send(email);
    }
}
