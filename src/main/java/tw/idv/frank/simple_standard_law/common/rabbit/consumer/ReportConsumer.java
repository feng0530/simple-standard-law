package tw.idv.frank.simple_standard_law.common.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import tw.idv.frank.simple_standard_law.common.rabbit.constant.RabbitConstants;
import tw.idv.frank.simple_standard_law.common.rabbit.dto.RabbitMsg;
import tw.idv.frank.simple_standard_law.schema.report.service.ReportService;

@Component
public class ReportConsumer {

    @Autowired
    private ReportService reportService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitConstants.NAME_REPORT_QUEUE, concurrency = "1-10")
    public void receiveReportRequest (@Payload RabbitMsg msg) {
        msg.setMsg(reportService.runIR01());
        rabbitTemplate.convertAndSend(msg.getExchange(), "mail.IR01", msg);
    }

}
