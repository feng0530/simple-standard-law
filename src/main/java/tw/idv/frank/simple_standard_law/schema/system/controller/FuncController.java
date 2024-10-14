package tw.idv.frank.simple_standard_law.schema.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.idv.frank.simple_standard_law.common.dto.CommonResult;
import tw.idv.frank.simple_standard_law.schema.system.model.entity.Func;
import tw.idv.frank.simple_standard_law.schema.system.service.FuncService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/funcs")
public class FuncController {

    @Autowired
    private FuncService funcService;

    @GetMapping
    public CommonResult<List<Func>> findFuncList() {
        return new CommonResult<>(funcService.findFuncList());
    }
}
