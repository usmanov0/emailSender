package com.example.emailsender.service;

import com.example.emailsender.models.Mail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;


@Service
public class EmailService {
    public final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender){
        this.mailSender = javaMailSender;
    }
    public void sendHTMLEmail(Mail message) throws MessagingException {
        MimeMessage emailMessage = mailSender.createMimeMessage();
        MimeMessageHelper mailBuilder = new MimeMessageHelper(emailMessage, true);
        mailBuilder.setTo(message.getMailTo());
        mailBuilder.setFrom(message.getMailFrom());
        mailBuilder.setText(message.getMailContent(),true);
        mailBuilder.setSubject(message.getMailSubject());
        mailSender.send(emailMessage);
    }


}
