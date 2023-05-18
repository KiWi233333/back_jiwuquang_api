package com.example.back_jiwuquang_api.domain.controller.admin;

import com.example.back_jiwuquang_api.domain.constant.JwtConstant;
import com.example.back_jiwuquang_api.dto.goods.GoodsDTO;
import com.example.back_jiwuquang_api.dto.goods.GoodsPageDTO;
import com.example.back_jiwuquang_api.dto.goods.UpdateGoodsDTO;
import com.example.back_jiwuquang_api.dto.sys.LoginDTO;
import com.example.back_jiwuquang_api.service.goods.GoodsService;
import com.example.back_jiwuquang_api.util.Result;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 管理员模块
 * #商品管理模块
 *
 * @className: AdminGoodsController
 * @author: Kiwi23333
 * @description: 商品管理模块
 * @date: 2023/5/5 22:08
 */
@Slf4j
@Api(value = "商品管理模块")
@RestController
@RequestMapping("/admin/goods")
public class AdminGoodsController {

    @Autowired
    GoodsService goodsService;

    @ApiOperation(value = "分页获取商品", tags = "商品管理模块")
    @GetMapping("/{page}/{size}")
    Result getGoodsListByPage(@ApiParam("页码") @PathVariable int page,
                              @ApiParam("每页个数") @PathVariable int size,
                              @RequestBody GoodsPageDTO goodsPageDTO) {
        // 查询所有商品
        return goodsService.getGoodsListByPageSize(goodsPageDTO, page, size, 1);
    }


    @ApiOperation(value = "查询商品信息", tags = "商品管理模块")
    @GetMapping("/{id}")
    Result getGoodsInfo(@ApiParam("商品id") @PathVariable String id) {
        return goodsService.getGoodsInfoById(id);
    }


    @ApiOperation(value = "添加商品", tags = "商品信息管理")
    @ApiImplicitParam(name = JwtConstant.HEADER_NAME, value = "管理员token", required = true)
    @PostMapping(value = "")
    Result addGoodsByGoodsDTO(@RequestHeader(name = JwtConstant.HEADER_NAME) String token,
                              @Valid @RequestBody GoodsDTO goodsDTO,
                              BindingResult result) {
        if (result.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(result.getFieldError().getDefaultMessage());
        } else {
            // 添加商品
            return goodsService.addGoodsByGoodsDTO(goodsDTO);
        }
    }

    @ApiOperation(value = "修改商品", tags = "商品信息管理")
    @ApiImplicitParam(name = JwtConstant.HEADER_NAME, value = "管理员token", required = true)
    @PutMapping(value = "")
    Result updateGoods(@RequestHeader(name = JwtConstant.HEADER_NAME) String token,
                       @Valid @RequestBody UpdateGoodsDTO upGoodsDTO,
                       BindingResult result) {
        if (result.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(result.getFieldError().getDefaultMessage());
        } else {
            // 添加商品
            return goodsService.updateGoods(upGoodsDTO);
        }
    }

    @ApiOperation(value = "删除商品（单个）", tags = "商品信息管理")
    @ApiImplicitParam(name = JwtConstant.HEADER_NAME, value = "管理员token", required = true)
    @DeleteMapping(value = "/{id}")
    Result deleteGoods(@RequestHeader(name = JwtConstant.HEADER_NAME) String token,
                       @PathVariable String id) {
        if (StringUtil.isNullOrEmpty(id)) {
            return Result.fail("商品id不能为空");
        } else {
            // 删除商品
            return goodsService.deleteGoodsById(id);
        }
    }


}
