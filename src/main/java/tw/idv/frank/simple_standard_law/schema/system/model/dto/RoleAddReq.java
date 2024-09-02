package tw.idv.frank.simple_standard_law.schema.system.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;



@Data
public class RoleAddReq {

    // 接收回傳的ID
    private Integer roleId;

    @NotBlank
    private String roleName;

    private RoleFuncAddReq roleFunc;
}
