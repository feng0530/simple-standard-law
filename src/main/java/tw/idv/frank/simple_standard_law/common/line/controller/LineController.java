package tw.idv.frank.simple_standard_law.common.line.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.idv.frank.simple_standard_law.common.dto.CommonResult;
import tw.idv.frank.simple_standard_law.common.exception.BaseException;
import tw.idv.frank.simple_standard_law.common.line.service.LineService;

@CrossOrigin("*")
@RestController
@RequestMapping("/line")
public class LineController {

    @Autowired
    private LineService lineService;

    @GetMapping("/authorize")
    public CommonResult redirectToLineOAuth() {

        return new CommonResult(lineService.getOAuthUrl());
    }

    @PostMapping("/getToken")
    public CommonResult getLineToken(@RequestParam String code) throws BaseException {
        return new CommonResult(lineService.getLineToken(code));
    }

    @PostMapping("/sendPicture")
    public CommonResult sendPicture(@RequestParam String token) throws BaseException {
        return new CommonResult(lineService.sendPicture(token));
    }
}
