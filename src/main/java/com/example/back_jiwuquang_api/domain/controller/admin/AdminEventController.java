package com.example.back_jiwuquang_api.domain.controller.admin;

import com.example.back_jiwuquang_api.dto.event.EventGoodsDTO;
import com.example.back_jiwuquang_api.service.event.EventGoodsService;
import com.example.back_jiwuquang_api.service.event.EventService;
import com.example.back_jiwuquang_api.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 管理员模块
 * #活动模块
 *
 * @className: EventController
 * @author: Kiwi23333
 * @description: 活动模块
 * @date: 2023/4/30 22:27
 */
@Slf4j
@Api(value = "活动模块")
@RestController
@RequestMapping("/admin/event")
public class AdminEventController {

    @Autowired
    EventService eventService;

    @ApiOperation(value = "获取全部活动列表", tags = "活动模块")
    @GetMapping("/list")
    Result getAllEventList() {
        return eventService.getAllEventList(1);
    }

    @ApiOperation(value = "获取活动商品", tags = "活动模块")
    @GetMapping("/goods/{id}")
    Result getEventGoodsList(@PathVariable String id) {
        return eventService.getEventGoodsList(id);
    }

    /************************ 活动商品关联表操作 ************************/

    @Autowired
    EventGoodsService eventGoodsService;

    @ApiOperation(value = "添加活动商品（单个）", tags = "活动模块")
    @PostMapping("/goods")
    Result addEventGoodsById(@Valid @RequestBody EventGoodsDTO eventGoodsDTO,
                             BindingResult result) {
        if (result.hasErrors()) {
            return Result.fail(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return eventGoodsService.addEventGoodsById(eventGoodsDTO);
    }
    @ApiOperation(value = "添加活动商品（批量）", tags = "活动模块")
    @PostMapping("/goods/list")
    Result addEventGoodsByBatch(@Valid @RequestBody List<EventGoodsDTO> list,
                                BindingResult result) {
        if (result.hasErrors()) {
            return Result.fail(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return eventGoodsService.addEventGoodsByBatch(list);
    }

}
