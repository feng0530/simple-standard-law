package tw.idv.frank.simple_standard_law.schema.system.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.idv.frank.simple_standard_law.common.dto.CommonResult;
import tw.idv.frank.simple_standard_law.common.dto.LoginReq;
import tw.idv.frank.simple_standard_law.common.dto.LoginRes;
import tw.idv.frank.simple_standard_law.common.exception.BaseException;
import tw.idv.frank.simple_standard_law.schema.system.model.dao.RoleFuncMapper;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersFunc;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRegisterReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRes;
import tw.idv.frank.simple_standard_law.schema.system.service.UsersService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private RoleFuncMapper mapper;

    @PostMapping
    public CommonResult<UsersRes> register(@Valid @RequestBody UsersRegisterReq req) throws BaseException {
        return new CommonResult<UsersRes>(usersService.usersRegister(req));
    }

    @PostMapping("/login")
    public CommonResult<LoginRes> login(@Valid @RequestBody LoginReq req) {
        return new CommonResult<LoginRes>(usersService.usersLogin(req));
    }

    @PostMapping("/logout")
    public CommonResult logout(HttpServletRequest req) {
        usersService.usersLogout(req);
        return new CommonResult();
    }
}
