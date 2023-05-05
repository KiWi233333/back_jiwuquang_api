package com.example.back_jiwuquang_api.core.controller.front;

import io.netty.util.internal.StringUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(value = "资源模块")
@RestController
@RequestMapping("/res")
public class ResController {
    @Value("${}")
    private static String BASE_PATH;

    @ApiOperation(value = "获取图片资源", tags = "图片模块")
    @ApiParam(name = "name",value = "图片名称")
    @GetMapping("/image/{name}")
    String getUserInfo(@PathVariable String name) {
        if (StringUtil.isNullOrEmpty(
                name)) return BASE_PATH+"default.png";
        return BASE_PATH+name;
    }
}
