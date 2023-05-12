package com.example.back_jiwuquang_api.domain.controller.admin;

import com.example.back_jiwuquang_api.dto.goods.GoodsSkuDTO;
import com.example.back_jiwuquang_api.dto.goods.UpdateGoodsSkuDTO;
import com.example.back_jiwuquang_api.dto.other.IdsList;
import com.example.back_jiwuquang_api.service.goods.GoodsSkuService;
import com.example.back_jiwuquang_api.util.Result;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.back_jiwuquang_api.domain.constant.JwtConstant.HEADER_NAME;

/**
 * 管理员模块
 * 商品规格模块
 *
 * @className: GoodsController
 * @author: Kiwi23333
 * @description: 商品规格模块
 * @date: 2023/5/1 16:07
 */
@Slf4j
@Api(value = "商品规格模块")
@RestController
@RequestMapping("/admin/goods")
public class AdminGoodsSkuController {
    @Autowired
    GoodsSkuService goodsSkuService;

    @ApiOperation(value = "获取商品规格", tags = "商品规格模块")
    @GetMapping("/sku")
    Result getGoodsSkuByGoodsById(@RequestParam String gid) {
        if (gid.equals("")) return Result.fail("商品id不能为空！");
        // 业务
        return goodsSkuService.getGoodsSkuByGoodsById(gid);
    }

    @ApiOperation(value = "添加商品规格", tags = "商品规格模块")
    @PostMapping("/sku")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    Result addGoodsSkuByGoods(@RequestHeader(name = HEADER_NAME) String token,
                              @Valid @RequestBody GoodsSkuDTO goodsSkuDTO,
                              BindingResult result) {
        if (result.hasErrors()) {
            return Result.fail(result.getFieldError().getDefaultMessage());
        }
        // 业务
        return goodsSkuService.addGoodsSkuByGoods(goodsSkuDTO);
    }

    @ApiOperation(value = "修改商品规格", tags = "商品规格模块")
    @PutMapping("/sku/{goodsId}/{skuId}")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    Result updateGoodsSkuByGoods(@RequestHeader(name = HEADER_NAME) String token,
                                 @PathVariable String goodsId,
                                 @PathVariable String skuId,
                                 @Valid @RequestBody UpdateGoodsSkuDTO updateGoodsSkuDTO,
                                 BindingResult result) {
        if (result.hasErrors()) return Result.fail(result.getFieldError().getDefaultMessage());
        // 业务
        return goodsSkuService.updateGoodsSku(goodsId,skuId,updateGoodsSkuDTO);
    }


    @ApiOperation(value = "删除商品规格（单个）", tags = "商品规格模块")
    @DeleteMapping("/sku/{goodsId}/{skuId}")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    Result deleteGoodsSkuById(@PathVariable String goodsId,
                              @PathVariable String skuId,
                              @RequestHeader(name = HEADER_NAME) String token) {
        if (StringUtil.isNullOrEmpty(goodsId) || StringUtil.isNullOrEmpty(skuId)) return Result.fail("规格id不能为空！");
        // 业务
        return goodsSkuService.deleteGoodsSkuById(goodsId, skuId);
    }

    @ApiOperation(value = "删除商品规格（批量）", tags = "商品规格模块")
    @DeleteMapping("/sku/{goodsId}")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    Result batchDeleteGoodsSkuByIds(@PathVariable String goodsId,
                                    @RequestBody IdsList idsList,
                                    @RequestHeader(name = HEADER_NAME) String token) {
        if (idsList.getIds().isEmpty()) return Result.fail("规格ids不能为空！");
        // 业务
        return goodsSkuService.batchDeleteGoodsSkuByIds(goodsId, idsList.getIds());
    }


}
