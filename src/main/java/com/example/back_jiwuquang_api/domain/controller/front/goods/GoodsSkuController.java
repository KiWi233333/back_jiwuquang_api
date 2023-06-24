package com.example.back_jiwuquang_api.domain.controller.front.goods;

import com.example.back_jiwuquang_api.dto.other.IdsList;
import com.example.back_jiwuquang_api.service.goods.GoodsCategoryService;
import com.example.back_jiwuquang_api.service.goods.GoodsSkuService;
import com.example.back_jiwuquang_api.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 商品模块
 * 商品规格模块
 *
 * @className: GoodsController
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/1 16:07
 */
@Slf4j
@Api(value = "商品规格模块")
@RestController
@RequestMapping("/goods")
public class GoodsSkuController {
    @Autowired
    GoodsSkuService goodsSkuService;

    @ApiOperation(value = "获取商品规格", tags = "商品规格模块")
    @GetMapping("/sku")
    Result getGoodsSkuByGoodsById(@RequestParam String gid) {
        if (gid.equals("")) return Result.fail("商品id不能为空！");
        // 业务
        return goodsSkuService.getGoodsSkuByGoodsById(gid);
    }


    @ApiOperation(value = "获取商品规格(规格ids)", tags = "商品规格模块")
    @PostMapping("/sku")
    Result getGoodsSkuListByIds(@RequestBody IdsList idsList) {
        if (idsList.getIds().isEmpty()) return Result.fail("参数不能为空！");
        // 业务
        return goodsSkuService.getGoodsSkuByGoodsByIds(idsList.getIds());
    }


}
