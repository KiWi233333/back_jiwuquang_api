package com.example.back_jiwuquang_api.domain.controller.front.comm;

import com.example.back_jiwuquang_api.dto.comm.SelectCommPostDTO;
import com.example.back_jiwuquang_api.impl.comm.CommPostServiceImpl;
import com.example.back_jiwuquang_api.service.comm.CommCategoryService;
import com.example.back_jiwuquang_api.service.comm.CommPostService;
import com.example.back_jiwuquang_api.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 社区模块
 * 社区分类模块
 *
 * @className: CommunityController
 * @author: Kiwi23333
 * @description: 社区模块
 * @date: 2023/4/30 22:27
 */
@Slf4j
@Api(value = "社区模块")
@RestController
@RequestMapping("/community/category")
public class CommCategoryController {
    @Autowired
    CommCategoryService categoryService;


    @ApiOperation(value = "获取社区分类", tags = "社区模块")
    @GetMapping("/list")
    Result getGoodsListByPage() {
        return categoryService.getAllCategoryTree();
    }
}
