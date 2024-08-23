package tw.idv.frank.simple_standard_law.schema.system.model.dao;

import org.apache.ibatis.annotations.Mapper;
import tw.idv.frank.simple_standard_law.common.dto.LoginReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRegisterReq;
import tw.idv.frank.simple_standard_law.schema.system.model.entity.Users;

@Mapper
public interface UsersMapper {

    int usersRegister(UsersRegisterReq req);

    Users findById(Integer userId);

    Users findByAccount(String account);

    Users usersLogin(LoginReq req);
}