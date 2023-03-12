package com.chicasensistemas.service;

import com.chicasensistemas.model.response.UserEmailResponse;
import com.chicasensistemas.service.abstraction.IEmailService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@AllArgsConstructor
public class EmailService implements IEmailService {
    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    @Async
    public void welcomeEmail(String mail, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setFrom("noreply@ecommerce.com");
        message.setSubject("Bienvenid@ a Chicas En Sistemas");
        message.setText("Bienvenid@ " + name + " a Chicas En Sistemas!");
        javaMailSender.send(message);
    }

    @Async
    public void recoveryPasswordMessage(String email, String resetToken, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("chiquesensistemasupport@demo.com");
        message.setTo(email);
        message.setSubject("Recuperar Contraseña");
        message.setText("Para actualizar tu contraseña, has click en el siguiente link:\n" + link
                + "/reset?token=" + resetToken);
        javaMailSender.send(message);
    }

    //in this method you need to specify the local file path
    //example:     sendMailSender.emailWithAttachment(user.getEmail(),"this is an email with arrachment...","this email has attachment","D:\\Cursos\\EGG\\Codes\\Html - Css\\Ej4\\index.html");
    public void emailWithAttachment(String toEmail, String body, String subject, String attachment) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("spring.email.from@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystem = new FileSystemResource(new File(attachment));

        mimeMessageHelper.addAttachment(fileSystem.getFilename(), fileSystem);

        javaMailSender.send(mimeMessage);
    }

    public String sendMail(UserEmailResponse user) throws MessagingException {
        Context context = new Context();
        context.setVariable("user", user);

        String process = templateEngine.process("emails/welcome", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Welcome " + user.getFirstName());
        helper.setText(process, true);
        helper.setTo(user.getEmail());
        javaMailSender.send(mimeMessage);
        return "Sent";
    }
}



