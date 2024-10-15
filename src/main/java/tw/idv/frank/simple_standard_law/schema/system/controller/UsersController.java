package tw.idv.frank.simple_standard_law.schema.system.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tw.idv.frank.simple_standard_law.common.constant.CommonCode;
import tw.idv.frank.simple_standard_law.common.dto.CommonResult;
import tw.idv.frank.simple_standard_law.common.dto.LoginReq;
import tw.idv.frank.simple_standard_law.common.dto.LoginRes;
import tw.idv.frank.simple_standard_law.common.exception.BaseException;
import tw.idv.frank.simple_standard_law.common.redis.service.RedisService;
import tw.idv.frank.simple_standard_law.common.tools.JsonTool;
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
    private RedisService redisService;

    @PostMapping("/register")
    public CommonResult<UsersRes> register(@Valid @RequestBody UsersRegisterReq req) throws BaseException {
        return new CommonResult<>(usersService.usersRegister(req));
    }

    @PostMapping("/login")
    public CommonResult<LoginRes> login(@Valid @RequestBody LoginReq req) {
        return new CommonResult<>(usersService.usersLogin(req));
    }

    @DeleteMapping("/logout")
    public CommonResult logout(HttpServletRequest http) {
        usersService.usersLogout(http);
        return new CommonResult();
    }

    @GetMapping
    public CommonResult<List<UsersRes>> findUsersList() {
        return new CommonResult<>(usersService.findUsersList());
    }

    @PreAuthorize("#userId == authentication.principal")
    @GetMapping("/{userId}/funcs")
    public CommonResult<List<UsersFunc>> findUserFuncByUserId(@PathVariable Integer userId) throws JsonProcessingException {
        List<UsersFunc> usersFuncList = JsonTool.convertJsonToObject(redisService.getAuthorities(String.valueOf(userId)), new TypeReference<List<UsersFunc>>() {});
        return new CommonResult<>(usersFuncList);
    }

    @DeleteMapping("/{userId}")
    public CommonResult deleteUserByUserId(@PathVariable Integer userId) {
        usersService.deleteByUserId(userId);
        return new CommonResult(CommonCode.DELETE);
    }
}
