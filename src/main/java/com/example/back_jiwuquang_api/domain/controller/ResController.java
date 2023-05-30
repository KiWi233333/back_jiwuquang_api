package com.example.back_jiwuquang_api.domain.controller;

import com.example.back_jiwuquang_api.domain.config.FileOSSConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.back_jiwuquang_api.domain.constant.JwtConstant.HEADER_NAME;
import static com.example.back_jiwuquang_api.domain.constant.UserConstant.USER_ID_KEY;

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
            log.info("Redirect fail {} ",e.getMessage());
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


    @ApiOperation(value = "用户上传图片（单个）", tags = "资源模块")
    @ApiParam(name = "file", value = "图片文件")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @PostMapping("/user/file")
    void toUpLoadImage(@RequestHeader(name = HEADER_NAME) String token,
                       @RequestParam(name = "file") MultipartFile file,
                       HttpServletRequest request)  {
        String userId = request.getAttribute(USER_ID_KEY).toString();
//        return usersService.updateUserAvatar(file, );
    }

}
