package tw.idv.frank.simple_standard_law.schema.system.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tw.idv.frank.simple_standard_law.common.dto.CommonResult;
import tw.idv.frank.simple_standard_law.common.dto.LoginReq;
import tw.idv.frank.simple_standard_law.common.dto.LoginRes;
import tw.idv.frank.simple_standard_law.common.exception.BaseException;
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

    @PostMapping("/register")
    public CommonResult<UsersRes> register(@Valid @RequestBody UsersRegisterReq req) throws BaseException {
        return new CommonResult<>(usersService.usersRegister(req));
    }

    @PostMapping("/login")
    public CommonResult<LoginRes> login(@Valid @RequestBody LoginReq req) {
        return new CommonResult<>(usersService.usersLogin(req));
    }

    @DeleteMapping("/logout")
    public CommonResult logout(HttpServletRequest req) {
        usersService.usersLogout(req);
        return new CommonResult();
    }

    @PreAuthorize("hasAuthority('root_*')")
    @GetMapping
    public CommonResult<List<UsersRes>> findUsersList() {
        return new CommonResult<>(usersService.findUsersList());
    }

    @PreAuthorize("hasAuthority('root_*') or #userId == authentication.principal")
    @GetMapping("/{userId}")
    public CommonResult<UsersRes> findByUserId(@PathVariable Integer userId) {
        return new CommonResult<>(usersService.findByUserId(userId));
    }
}
