package com.example.back_jiwuquang_api.domain.controller.front.comm;

import com.example.back_jiwuquang_api.dto.comm.SelectCommPostCommentDTO;
import com.example.back_jiwuquang_api.dto.comm.SelectCommUserDTO;
import com.example.back_jiwuquang_api.impl.comm.CommPostCommentServiceImpl;
import com.example.back_jiwuquang_api.service.sys.UserService;
import com.example.back_jiwuquang_api.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 社区模块
 *
 * @className: CommController
 * @author: Kiwi23333
 * @description: 社区模块
 * @date: 2023/6/24 1:44
 */

@Slf4j
@Api(value = "社区模块")
@RestController
@RequestMapping("/community")
public class CommController {
    @Autowired
    UserService userService;
    @ApiOperation(value = "获取社区用户列表（分页）", tags = "社区模块")
    @PostMapping("/user")
    Result getGoodsListByPage(@ApiParam("页码") @PathVariable int page,
                              @ApiParam("每页个数") @PathVariable int size,
                              @RequestBody SelectCommUserDTO dto) {
        return userService.getCommUserList(dto, page, size);
    }
}
