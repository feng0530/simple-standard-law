package tw.idv.frank.simple_standard_law.schema.system.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleFuncRes {

    private Integer roleId;

    private String roleName;

    private List<RoleFuncDto> funcList;
}
