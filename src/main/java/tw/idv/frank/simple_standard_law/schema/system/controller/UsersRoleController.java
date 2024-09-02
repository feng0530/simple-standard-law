package tw.idv.frank.simple_standard_law.schema.system.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tw.idv.frank.simple_standard_law.common.constant.CommonCode;
import tw.idv.frank.simple_standard_law.common.dto.CommonResult;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRoleReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRoleRes;
import tw.idv.frank.simple_standard_law.schema.system.service.UsersRoleService;

import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("/users/{userId}/roles")
public class UsersRoleController {

    @Autowired
    private UsersRoleService usersRoleService;

    @PreAuthorize("hasAuthority('root_*')")
    @PutMapping
    public CommonResult updateUsersRole(
            @PathVariable Integer userId,
            @Valid @RequestBody UsersRoleReq req
    ) {
        req.setUserId(userId);
        usersRoleService.updateUsersRole(req);
        return new CommonResult(CommonCode.UPDATE);
    }

    @PreAuthorize("hasAuthority('root_*')")
    @GetMapping
    public CommonResult<List<UsersRoleRes>> findUsersRoleByUserId(@PathVariable Integer userId) {
        return new CommonResult<>(usersRoleService.findUsersRoleByUserId(userId));
    }
}
