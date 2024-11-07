package tw.idv.frank.simple_standard_law.schema.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tw.idv.frank.simple_standard_law.common.dto.CommonResult;
import tw.idv.frank.simple_standard_law.common.exception.BaseException;
import tw.idv.frank.simple_standard_law.common.rabbit.dto.RabbitMsg;
import tw.idv.frank.simple_standard_law.schema.report.service.ReportService;

@CrossOrigin("*")
@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PreAuthorize("hasAnyAuthority('root_x','IR01_x')")
    @PostMapping("/IR01")
    public CommonResult runIR01(@RequestBody RabbitMsg msg) throws BaseException {
        reportService.addTaskToReportQueue(msg);
        return new CommonResult();
    }
    @PreAuthorize("hasAnyAuthority('root_x','IR01_x')")
    @PostMapping("/IR01Test")
    public CommonResult runIR01Test() throws BaseException {
        reportService.runIR01Test();
        return new CommonResult();
    }

    @PreAuthorize("hasAnyAuthority('root_x','IR01_x','IR01_r')")
    @GetMapping("/IR01")
    public CommonResult getIR01() throws BaseException {
        return new CommonResult(reportService.getIR01());
    }
}
