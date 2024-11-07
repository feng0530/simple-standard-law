package tw.idv.frank.simple_standard_law.common.rabbit.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RabbitMsg implements Serializable {

    private String msg;

    private String exchange;

    private String routingKey;
}
