package tw.idv.frank.simple_standard_law.schema.system.service;

import jakarta.servlet.http.HttpServletRequest;
import tw.idv.frank.simple_standard_law.common.dto.LoginReq;
import tw.idv.frank.simple_standard_law.common.dto.LoginRes;
import tw.idv.frank.simple_standard_law.common.exception.BaseException;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRegisterReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRes;

import java.util.List;

public interface UsersService {

    UsersRes usersRegister(UsersRegisterReq req) throws BaseException;

    LoginRes usersLogin(LoginReq req);

    void usersLogout(HttpServletRequest http);

    List<UsersRes> findUsersList();

    UsersRes findByUserId(Integer userId) throws BaseException;

    void deleteByUserId(Integer userId);
}
