package tw.idv.frank.simple_standard_law.schema.system.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tw.idv.frank.simple_standard_law.common.constant.CommonCode;
import tw.idv.frank.simple_standard_law.common.dto.CommonResult;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleFuncAddReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleFuncRes;
import tw.idv.frank.simple_standard_law.schema.system.service.RoleFuncService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/roles/{roleId}/funcs")
public class RoleFuncController {

    @Autowired
    private RoleFuncService roleFuncService;

    @PreAuthorize("hasAuthority('root_*')")
    @PutMapping
    public CommonResult updateRoleFuncFunc(
            @PathVariable Integer roleId,
            @Valid @RequestBody RoleFuncAddReq req
    ) {
        req.setRoleId(roleId);
        roleFuncService.updateRoleFunc(req);
        return new CommonResult(CommonCode.UPDATE);
    }

    @PreAuthorize("hasAuthority('root_*')")
    @GetMapping
    public CommonResult<List<RoleFuncRes>> findRoleFuncByRoleId(@PathVariable Integer roleId) {
        return new CommonResult<>(roleFuncService.findRoleFuncByRoleId(roleId));
    }
}
