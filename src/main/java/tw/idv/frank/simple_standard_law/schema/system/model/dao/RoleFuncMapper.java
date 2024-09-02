package tw.idv.frank.simple_standard_law.schema.system.model.dao;

import org.apache.ibatis.annotations.Mapper;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleFuncAddReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleFuncRes;

import java.util.List;

@Mapper
public interface RoleFuncMapper {

    List<String> findUsersFuncByUsersId(Integer usersId);

    void addRoleFunc(RoleFuncAddReq req);

    void deleteByRoleId(Integer roleId);

    List<RoleFuncRes> findRoleFuncByRoleId(Integer roleId);
}