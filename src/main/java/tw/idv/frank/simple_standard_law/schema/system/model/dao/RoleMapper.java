package tw.idv.frank.simple_standard_law.schema.system.model.dao;

import org.apache.ibatis.annotations.Mapper;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleAddReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleRes;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleUpdateReq;
import tw.idv.frank.simple_standard_law.schema.system.model.entity.Role;

import java.util.List;

@Mapper
public interface RoleMapper {

    void addRole(RoleAddReq req);

    List<Role>findRoleList();

    void updateRole(RoleUpdateReq req);

    void deleteRole(Integer roleId);
}