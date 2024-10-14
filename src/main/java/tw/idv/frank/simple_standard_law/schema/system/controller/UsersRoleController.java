package tw.idv.frank.simple_standard_law.schema.system.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tw.idv.frank.simple_standard_law.common.constant.CommonCode;
import tw.idv.frank.simple_standard_law.common.dto.CommonResult;
import tw.idv.frank.simple_standard_law.common.exception.BaseException;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRoleReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRoleRes;
import tw.idv.frank.simple_standard_law.schema.system.service.UsersRoleService;


@CrossOrigin("*")
@RestController
@RequestMapping("/users/{userId}/roles")
public class UsersRoleController {

    @Autowired
    private UsersRoleService usersRoleService;

    @PutMapping
    public CommonResult updateUsersRole(
            @PathVariable Integer userId,
            @Valid @RequestBody UsersRoleReq req
    ) throws BaseException {

        // 取得當前的 Authentication 物件
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 取得 principal
        Integer user = (Integer)authentication.getPrincipal();
        if(user == userId) {
            throw new BaseException(400, "無法修改自身的角色權限");
        }

        req.setUserId(userId);
        usersRoleService.updateUsersRole(req);
        return new CommonResult(CommonCode.UPDATE);
    }

    @GetMapping
    public CommonResult<UsersRoleRes> findUsersRoleByUserId(@PathVariable Integer userId) {
        return new CommonResult<>(usersRoleService.findUsersRoleByUserId(userId));
    }
}
