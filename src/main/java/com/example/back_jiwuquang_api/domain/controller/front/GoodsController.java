package com.example.back_jiwuquang_api.domain.controller.front;

import com.example.back_jiwuquang_api.dto.goods.GoodsPageDTO;
import com.example.back_jiwuquang_api.service.goods.GoodsService;
import com.example.back_jiwuquang_api.util.Result;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{page}/{size}")
    Result getGoodsListByPage(@ApiParam("页码") @PathVariable int page,
                              @ApiParam("每页个数") @PathVariable int size,
                              @RequestBody GoodsPageDTO goodsPageDTO) {
        return goodsService.getGoodsListByPageSize(goodsPageDTO, page, size,1);
    }

    @ApiOperation(value = "查询商品信息", tags = "商品信息模块")
    @GetMapping("/{id}")
    Result getGoodsInfo(@ApiParam("商品id") @PathVariable String id) {
        return goodsService.getGoodsInfoById(id);
    }

}
