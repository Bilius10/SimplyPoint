package com.Symple.Point.SERVICE;

import com.Symple.Point.DTO.Saida.EnviarEmailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    final JavaMailSender emailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    public void sendEmail(EnviarEmailDTO emailModel) {
        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(emailModel.emailTo());
            message.setSubject(emailModel.subject());
            message.setText(emailModel.text());

            emailSender.send(message);

        } catch (MailException e) {
            logger.error("Erro ao enviar e-mail: ", e);
        }
    }

}
