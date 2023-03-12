package com.chicasensistemas.service.abstraction;

import javax.mail.MessagingException;

public interface IEmailService {

    void welcomeEmail(String mail, String name);

    void recoveryPasswordMessage(String email, String resetToken, String link);

    void emailWithAttachment(String toEmail, String body, String subject, String attachment) throws MessagingException;
}
