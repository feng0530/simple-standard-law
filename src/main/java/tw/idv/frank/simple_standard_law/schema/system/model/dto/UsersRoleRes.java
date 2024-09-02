package tw.idv.frank.simple_standard_law.schema.system.model.dto;

import lombok.Data;
import tw.idv.frank.simple_standard_law.schema.system.model.entity.Role;

import java.util.List;


@Data
public class UsersRoleRes {

    private Integer userId;

    private String userName;

    private List<UserRoleDto> roleList;
}
