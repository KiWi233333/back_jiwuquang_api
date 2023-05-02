package com.example.kiwi_community_mall_back.core.controller.front;

import com.example.kiwi_community_mall_back.dto.goods.GoodsCategoryVO;
import com.example.kiwi_community_mall_back.service.GoodsCategoryService;
import com.example.kiwi_community_mall_back.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 商品模块
 * @className: GoodsController
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/1 16:07
 */
@Slf4j
@Api(value = "商品模块")
@RestController
@RequestMapping("/category")
public class GoodsCategoryController {
    @Autowired
    GoodsCategoryService goodsCategoryService;

    @ApiOperation(value = "获取所有分类", tags = "商品分类模块")
    @GetMapping("/list")
    Result getAllCategoryTree() {
        return goodsCategoryService.getAllCategoryTree();
    }


}
