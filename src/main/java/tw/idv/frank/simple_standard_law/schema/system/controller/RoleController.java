package tw.idv.frank.simple_standard_law.schema.system.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tw.idv.frank.simple_standard_law.common.constant.CommonCode;
import tw.idv.frank.simple_standard_law.common.dto.CommonResult;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleAddReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleRes;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleUpdateReq;
import tw.idv.frank.simple_standard_law.schema.system.service.RoleService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasAuthority('root_x')")
    @PostMapping
    public CommonResult addRole(@Valid @RequestBody RoleAddReq req) {
        roleService.addRole(req);
        return new CommonResult(CommonCode.CREATE);
    }

    @PreAuthorize("hasAuthority('root_x')")
    @GetMapping
    public CommonResult<List<RoleRes>> findRoleList() {
        return new CommonResult<> (roleService.findRoleList());
    }

    @PreAuthorize("hasAuthority('root_x')")
    @PutMapping
    public CommonResult updateRole(@Valid @RequestBody RoleUpdateReq req) {
        roleService.updateRole(req);
        return new CommonResult(CommonCode.UPDATE);
    }

    @PreAuthorize("hasAuthority('root_x')")
    @DeleteMapping("/{roleId}")
    public CommonResult deleteRole(@PathVariable Integer roleId) {
        roleService.deleteRole(roleId);
        return new CommonResult(CommonCode.DELETE);
    }
}
