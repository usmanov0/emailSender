package com.example.emailsender.controller;

import com.example.emailsender.builders.MailBuilder;
import com.example.emailsender.models.Mail;
import com.example.emailsender.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class EmailController {
    private final EmailService emailService;
    private final String mailTo;

    public EmailController(EmailService emailService,@Value("${mail.to}") String mailTo) {
        this.emailService = emailService;
        this.mailTo = mailTo;
    }

    @RequestMapping(value = "/test")
    public String sendTestReport(HttpServletRequest request){
        final Mail mail = new MailBuilder()
                .From(null)
                .To(this.mailTo)
                .Template("mail-template.html")
                .AddContext("subject", "Test Email")
                .AddContext("content", "Hello World!")
                .Subject("Hello")
                .createMail();
        String responseMessage = request.getRequestURI();
        try{
            this.emailService.sendHTMLEmail(mail);
        } catch (Exception e) {
            responseMessage = "Request Unsuccessful \n"+ e.getMessage() + "\n" + responseMessage;
            return responseMessage;
        }
        responseMessage = "Request Successful \n" + responseMessage;
        return responseMessage;
    }
    @RequestMapping(value = "/")
    public String homePage(HttpServletRequest request) {
        String responseMessage = request.getRequestURI();
        responseMessage = "Welcome to mailing service. \n" +
                "Please use /test to send sample report \n" + responseMessage;
        return responseMessage;
    }
}
