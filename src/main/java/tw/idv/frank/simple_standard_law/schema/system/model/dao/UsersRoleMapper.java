package tw.idv.frank.simple_standard_law.schema.system.model.dao;

import org.apache.ibatis.annotations.Mapper;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRoleReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRoleRes;

import java.util.List;


@Mapper
public interface UsersRoleMapper {

    void addUsersRole(UsersRoleReq req);

    void deleteByUserId(Integer usersId);

    UsersRoleRes findUsersRoleByUserId(Integer userId);
}