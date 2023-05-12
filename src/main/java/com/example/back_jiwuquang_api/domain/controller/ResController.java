package com.example.back_jiwuquang_api.domain.controller;

import com.example.back_jiwuquang_api.domain.config.FileOSSConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 资源模块
 *
 * @className: ResController
 * @author: Kiwi23333
 * @description: 公共图片、视频等资源
 * @date: 2023/5/5 15:08
 */
@Slf4j
@Api(value = "资源模块" )
@RestController
@RequestMapping("/res")
public class ResController {

    @Autowired
    FileOSSConfig fileOSSConfig;

    @ApiOperation(value = "图片资源", tags = "资源模块")
    @GetMapping("/image/{name}")
    void getImage(@PathVariable String name,HttpServletResponse response) throws IOException {
        try {
            response.sendRedirect(fileOSSConfig.hostName+"/image/"+name);
        }catch (Exception e) {
            log.info("Redirect fail {}",e.getMessage());
            response.sendError(404);
        }
    }
    @ApiOperation(value = "视频资源", tags = "资源模块")
    @GetMapping("/video/{name}")
    void getVideo(@PathVariable String name,HttpServletResponse response) throws IOException {
        try {
            response.sendRedirect(fileOSSConfig.hostName+"/video/"+name);
        }catch (Exception e) {
            log.info("Redirect fail {}",e.getMessage());
            response.sendError(404);
        }
    }
    @ApiOperation(value = "文件资源", tags = "资源模块")
    @GetMapping("/file/{name}")
    void getFile(@PathVariable String name,HttpServletResponse response) throws IOException {
        try {
            response.sendRedirect(fileOSSConfig.hostName+"/file/"+name);
        }catch (Exception e) {
            log.info("Redirect fail {}",e.getMessage());
            response.sendError(404);
        }
    }
}
