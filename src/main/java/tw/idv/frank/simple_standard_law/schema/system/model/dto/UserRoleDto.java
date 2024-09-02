package tw.idv.frank.simple_standard_law.schema.system.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserRoleDto {

    private Integer roleId;

    private String roleName;

    private Date createTime;
}
