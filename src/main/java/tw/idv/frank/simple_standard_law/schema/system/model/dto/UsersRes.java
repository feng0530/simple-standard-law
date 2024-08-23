package tw.idv.frank.simple_standard_law.schema.system.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UsersRes {

    private Integer userId;

    private String userName;

    private String account;

    private Date createTime;
}
