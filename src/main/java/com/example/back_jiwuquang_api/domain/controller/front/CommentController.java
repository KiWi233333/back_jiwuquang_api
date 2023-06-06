package com.example.back_jiwuquang_api.domain.controller.front;

import com.example.back_jiwuquang_api.service.orders.OrdersCommentService;
import com.example.back_jiwuquang_api.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品模块
 * 商品评价模块
 *
 * @className: GoodsController
 * @author: Kiwi23333
 * @description: 商品评价模块
 * @date: 2023/5/1 16:07
 */
@Slf4j
@Api(value = "商品评价模块")
@RestController
@RequestMapping("/goods/comments")
public class CommentController {

    @Autowired
    OrdersCommentService orderCommentService;

    @ApiOperation(value = "查询商品评价", tags = "商品信息模块")
    @GetMapping("/{page}/{size}/{gid}")
    Result getCommentsByGId(
            @ApiParam("页码") @PathVariable int page,
            @ApiParam("个数") @PathVariable int size,
            @ApiParam("商品id") @PathVariable String gid) {
        return orderCommentService.getCommentsByGId(page, size, gid);
    }


}
