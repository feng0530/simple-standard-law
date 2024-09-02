package tw.idv.frank.simple_standard_law.schema.system.service;

import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleAddReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleRes;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleUpdateReq;

import java.util.List;

public interface RoleService {

    void addRole(RoleAddReq req);

    List<RoleRes> findRoleList();

    void updateRole(RoleUpdateReq req);

    void deleteRole(Integer roleId);
}
