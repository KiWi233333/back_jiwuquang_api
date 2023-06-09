package com.example.back_jiwuquang_api.domain.controller.front.comm;

import com.example.back_jiwuquang_api.dto.comm.SelectCommPostCommentDTO;
import com.example.back_jiwuquang_api.impl.comm.CommPostCommentServiceImpl;
import com.example.back_jiwuquang_api.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 社区模块
 * 帖子评论模块
 *
 * @className: CommunityController
 * @author: Kiwi23333
 * @description: 社区模块
 * @date: 2023/4/30 22:27
 */
@Slf4j
@Api(value = "社区模块")
@RestController
@RequestMapping("/community/post/comment")
public class CommPostCommentController {
    @Autowired
    CommPostCommentServiceImpl commPostCommentServiceImpl;


    @ApiOperation(value = "获取社区帖子评论列表（分页）", tags = "社区模块")
    @GetMapping("/list/{cid}/{page}/{size}")
    Result getGoodsListByPage(@ApiParam("帖子id") @PathVariable String cid,
                              @ApiParam("页码") @PathVariable int page,
                              @ApiParam("每页个数") @PathVariable int size,
                              @RequestBody SelectCommPostCommentDTO dto) {
        return commPostCommentServiceImpl.getCommPostCommentList(dto, cid, page, size);
    }
}
