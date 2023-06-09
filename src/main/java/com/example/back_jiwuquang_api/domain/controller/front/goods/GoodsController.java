package com.example.back_jiwuquang_api.domain.controller.front.goods;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.back_jiwuquang_api.dto.goods.GoodsPageDTO;
import com.example.back_jiwuquang_api.dto.other.IdsList;
import com.example.back_jiwuquang_api.service.goods.GoodsActionService;
import com.example.back_jiwuquang_api.service.goods.GoodsService;
import com.example.back_jiwuquang_api.util.Result;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.example.back_jiwuquang_api.domain.constant.JwtConstant.HEADER_NAME;
import static com.example.back_jiwuquang_api.domain.constant.UserConstant.USER_ID_KEY;

/**
 * 商品模块
 *
 * @className: GoodsController
 * @author: Kiwi23333
 * @description: 商品模块
 * @date: 2023/5/1 16:07
 */
@Slf4j
@Api(value = "商品信息模块")
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @ApiOperation(value = "分页获取商品", tags = "商品信息模块")
    @GetMapping("/list/{page}/{size}")
    Result getGoodsListByPage(@ApiParam("页码") @PathVariable int page,
                              @ApiParam("每页个数") @PathVariable int size,
                              @RequestBody GoodsPageDTO goodsPageDTO) {
        return goodsService.getGoodsListByPageSize(goodsPageDTO, page, size, 0);
    }

    @ApiOperation(value = "查询商品信息", tags = "商品信息模块")
    @GetMapping("/item/{id}")
    Result getGoodsInfo(@ApiParam("商品id") @PathVariable String id) {
        return goodsService.getGoodsInfoById(id);
    }


    /******************************* 商品点赞、收藏 类 ************************************/
    @Autowired
    GoodsActionService goodsActionService;

    @ApiOperation(value = "获取商品收藏列表", tags = "商品信息模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @GetMapping("/action/collect")
    Result getGoodsCollect(@RequestHeader(name = HEADER_NAME) String token,
                           HttpServletRequest request) {
        String userId = String.valueOf(request.getAttribute(USER_ID_KEY));
        // 业务
        return goodsActionService.getGoodsCollect(userId);
    }

    @ApiOperation(value = "查询是否收藏商品", tags = "商品信息模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @GetMapping("/action/collect/{gid}")
    Result isGoodsCollect(@RequestHeader(name = HEADER_NAME) String token,
                          HttpServletRequest request,
                          @PathVariable String gid) {
        String userId = String.valueOf(request.getAttribute(USER_ID_KEY));
        // 业务
        return goodsActionService.isGoodsCollect(userId, gid);
    }

    @ApiOperation(value = "添加删除商品收藏", tags = "商品信息模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @PutMapping("/action/collect/{gid}")
    Result addGoodsCollectionByGid(@RequestHeader(name = HEADER_NAME) String token,
                                   @PathVariable String gid,
                                   HttpServletRequest request) {
        if (StringUtils.isBlank(gid)) return Result.fail(Result.NULL_ERR, "商品id不能为空！");
        String userId = String.valueOf(request.getAttribute(USER_ID_KEY));
        // 业务
        return goodsActionService.addGoodsCollectionByGid(userId, gid);
    }


    @ApiOperation(value = "取消商品收藏（批量）", tags = "商品信息模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @DeleteMapping("/action/collect")
    Result deleteBatchGoodsAction(@RequestHeader(name = HEADER_NAME) String token,
                                  @RequestBody IdsList ids,
                                  HttpServletRequest request) {
        if (ids.getIds().isEmpty()) return Result.fail(Result.NULL_ERR, "ids不能为空！");
        String userId = String.valueOf(request.getAttribute(USER_ID_KEY));
        // 业务
        try {
            return goodsActionService.deleteBatchGoodsAction(userId, ids.getIds());
        } catch (RuntimeException e) {
            return Result.fail(Result.DELETE_ERR, "删除失败，部分收藏不存在！");
        }
    }


}
