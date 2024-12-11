package tw.idv.frank.simple_standard_law.common.mail.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tw.idv.frank.simple_standard_law.common.rabbit.dto.RabbitMsg;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(RabbitMsg msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("fff80705@gmail.com");
        message.setSubject(msg.getMsg());
        message.setText(msg.toString());
        mailSender.send(message);
    }
}
