package com.demo.service.Email;
import org.springframework.mail.SimpleMailMessage;

public interface EmailServiceSMM /*extends EmailService<SimpleMailMessage>*/ {
    void sendEmail(SimpleMailMessage mailSender);
}
