package org.todo.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String email, String name, String token) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Verify your email address");

        String verificationLink =
                "http://localhost:8080/auth/verify-email?token=" + token;

        message.setText(
                "Hello " + name + ",\n\n" +
                        "Thank you for registering.\n\n" +
                        "Please verify your email using the link below:\n" +
                        verificationLink + "\n\n" +
                        "This link expires in 5 minutes.\n\n" +
                        "If you did not create this account, you can ignore this email."
        );

        mailSender.send(message);
    }
}
