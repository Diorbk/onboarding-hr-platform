package uk.ac.cf.spring.Group13Project1.email.domain;


import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class EmailServices {

    private final JavaMailSender javaMailSender;

    public EmailServices(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @PostConstruct
    public void sendEmailWithAttachment() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();


        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("benjones127777@gmail.com");
        mimeMessageHelper.setTo("newstarterbipsync@gmail.com");
        mimeMessageHelper.setText("Hello, I am Ben (Head of IT). I have enrolled you as a new employee onto our Employee Database, please go to attached link where you will be taken to a form page to fill out all specific information before your first day with us very soon. Glad to have you on the team and, I hope you enjoy your time at Bipsync. See you soon, Ben");
        mimeMessageHelper.setSubject("Welcome to Bipsync!");
        if (mimeMessage.getSentDate() == null) {
            mimeMessage.setSentDate(new Date());
        }

            javaMailSender.send(mimeMessage);

        System.out.println("Mail sent to newstarterbipsync@gmail.com");
    }


}



