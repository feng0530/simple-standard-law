package tw.idv.frank.simple_standard_law.schema.system.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleUpdateReq {

    @NotNull
    private Integer roleId;

    @NotBlank
    private String roleName;
}
