package tw.idv.frank.simple_standard_law.schema.system.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UsersRoleReq {

    private Integer userId;

    @NotNull
    private List<Integer> roleIdList;
}
