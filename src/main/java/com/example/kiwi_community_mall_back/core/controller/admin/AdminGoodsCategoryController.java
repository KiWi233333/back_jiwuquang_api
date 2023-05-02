package com.example.kiwi_community_mall_back.core.controller.admin;

import com.example.kiwi_community_mall_back.dto.goods.GoodsCategoryVO;
import com.example.kiwi_community_mall_back.dto.other.IdsList;
import com.example.kiwi_community_mall_back.service.GoodsCategoryService;
import com.example.kiwi_community_mall_back.util.Result;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 管理员模块
 * 商品分类管理
 * @className: GoodsController
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/1 16:07
 */
@Slf4j
@Api(value = "商品分类管理")
@RestController
@RequestMapping("/admin/category")
public class AdminGoodsCategoryController {
    @Autowired
    GoodsCategoryService goodsCategoryService;
    @ApiOperation(value = "添加分类", tags = "商品分类管理")
    @ApiImplicitParam(name = "Authorization", value = "管理员token", required = true)
    @PutMapping("/one")
    Result addCategoryByGoodsCategoryVO(@Valid @RequestBody GoodsCategoryVO goodsCategoryVO, BindingResult result) {
        if (result.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(result.getFieldError().getDefaultMessage());
        } else {
            return goodsCategoryService.addCategoryByOne(goodsCategoryVO);
        }
    }
    @ApiOperation(value = "批量添加分类", tags = "商品分类管理")
    @ApiImplicitParam(name = "Authorization", value = "管理员token", required = true)
    @PutMapping("/some")
    Result addCategoryByList(@Valid @RequestBody List<GoodsCategoryVO> categoryVOList, BindingResult result) {
        if (result.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(result.getFieldError().getDefaultMessage());
        } else {
            return goodsCategoryService.addCategoryByList(categoryVOList);
        }
    }

    @ApiOperation(value = "删除分类", tags = "商品分类管理")
    @ApiImplicitParam(name = "Authorization", value = "管理员token", required = true)
    @DeleteMapping("/one/{id}")
    Result deleteOneByCategoryId(@RequestHeader(name="Authorization") String token, @PathVariable String id) {
        if (StringUtil.isNullOrEmpty(id)) return Result.fail("id不能为空！");
        return goodsCategoryService.deleteOneByCategoryId(id);
    }


    @ApiOperation(value = "批量删除分类" ,notes = "可跨级删除", tags = "商品分类管理")
    @ApiImplicitParam(name = "Authorization", value = "管理员token", required = true)
    @DeleteMapping("/some")
    Result deleteByCategoryIds(@RequestHeader(name="Authorization") String token, @RequestBody IdsList idsList) {
        if (idsList.getIds().isEmpty()) return Result.fail("ids不能为空！");
        return goodsCategoryService.deleteByCategoryIds(idsList.getIds());
    }


}
