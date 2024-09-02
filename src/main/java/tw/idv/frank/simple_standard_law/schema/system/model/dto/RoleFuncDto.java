package tw.idv.frank.simple_standard_law.schema.system.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RoleFuncDto {

    private Integer funcId;

    private String funcName;

    private String level;

    private Date createTime;
}
