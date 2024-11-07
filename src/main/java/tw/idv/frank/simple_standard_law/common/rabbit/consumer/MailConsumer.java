package tw.idv.frank.simple_standard_law.common.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import tw.idv.frank.simple_standard_law.common.mail.service.MailService;
import tw.idv.frank.simple_standard_law.common.rabbit.constant.RabbitConstants;
import tw.idv.frank.simple_standard_law.common.rabbit.dto.RabbitMsg;

@Component
public class MailConsumer {

    @Autowired
    private MailService mailService;

    @RabbitListener(queues = RabbitConstants.NAME_MAIL_QUEUE, concurrency = "1-10")
    public void receiveMailRequest(@Payload RabbitMsg msg) {
        mailService.sendSimpleEmail(msg);
    }
}
