package com.example.back_jiwuquang_api.domain.controller.admin;

import com.example.back_jiwuquang_api.dto.event.EventDTO;
import com.example.back_jiwuquang_api.dto.event.EventGoodsDTO;
import com.example.back_jiwuquang_api.dto.other.IdsList;
import com.example.back_jiwuquang_api.service.event.EventGoodsService;
import com.example.back_jiwuquang_api.service.event.EventService;
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
import java.util.List;
import java.util.Objects;

import static com.example.back_jiwuquang_api.domain.constant.JwtConstant.HEADER_NAME;

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
    /************************ 活动操作 ************************/
    @Autowired
    EventService eventService;

    @ApiOperation(value = "获取全部活动列表", tags = "活动模块")
    @GetMapping("/list")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    Result getAllEventList(@RequestHeader(name = HEADER_NAME) String token) {
        return eventService.getAllEventList(1);
    }

    @ApiOperation(value = "获取活动商品", tags = "活动模块")
    @GetMapping("/goods/{id}")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    Result getEventGoodsList(@PathVariable String id,
                             @RequestHeader(name = HEADER_NAME) String token) {
        return eventService.getEventGoodsList(id);
    }


    @ApiOperation(value = "添加活动", tags = "活动模块")
    @PostMapping("/")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    Result updateEventById(@Valid @RequestBody EventDTO eventDTO,
                           @RequestHeader(name = HEADER_NAME) String token,
                           BindingResult result) {
        if (result.hasErrors()) {
            return Result.fail(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return eventService.addEventByEventDTO(eventDTO);
    }


    @ApiOperation(value = "修改活动", tags = "活动模块")
    @PutMapping("/{id}")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    Result updateEventById(@Valid @RequestBody EventDTO eventDTO,
                           @RequestHeader(name = HEADER_NAME) String token,
                           @PathVariable String id,
                           BindingResult result) {
        if (result.hasErrors()) {
            return Result.fail(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return eventService.updateEventById(id, eventDTO);
    }

    @ApiOperation(value = "删除活动", tags = "活动模块")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    Result deleteEventById(@PathVariable String id,
                           @RequestHeader(name = HEADER_NAME) String token,
                           BindingResult result) {
        if (StringUtil.isNullOrEmpty(id)) {
            return Result.fail(Result.NULL_ERR, "活动id不能为空！");
        }
        return eventService.deleteEventById(id);
    }


    /************************ 活动商品关联表操作 ************************/

    @Autowired
    EventGoodsService eventGoodsService;

    @ApiOperation(value = "添加活动商品（单个）", tags = "活动模块")
    @PostMapping("/goods")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    Result addEventGoodsById(@Valid @RequestBody EventGoodsDTO eventGoodsDTO,
                             @RequestHeader(name = HEADER_NAME) String token,
                             BindingResult result) {
        if (result.hasErrors()) {
            return Result.fail(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return eventGoodsService.addEventGoodsById(eventGoodsDTO);
    }

    @ApiOperation(value = "添加活动商品（批量）", tags = "活动模块")
    @PostMapping("/goods/list")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    Result addEventGoodsByBatch(@Valid @RequestBody List<EventGoodsDTO> list,
                                @RequestHeader(name = HEADER_NAME) String token,
                                BindingResult result) {
        if (result.hasErrors()) {
            return Result.fail(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return eventGoodsService.addEventGoodsByBatch(list);
    }

    @ApiOperation(value = "修改活动商品", tags = "活动模块")
    @PutMapping("/goods")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    Result updateEventGoodsById(@Valid @RequestBody EventGoodsDTO eventGoodsDTO,
                                @RequestHeader(name = HEADER_NAME) String token,
                                BindingResult result) {
        if (result.hasErrors()) {
            return Result.fail(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return eventGoodsService.updateEventGoodsById(eventGoodsDTO);
    }


    @ApiOperation(value = "删除活动商品（单个）", tags = "活动模块")
    @DeleteMapping("/goods/{eid}/{id}")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    Result deleteEventById(@PathVariable String eid,
                           @PathVariable String gid,
                           @RequestHeader(name = HEADER_NAME) String token) {
        if (StringUtil.isNullOrEmpty(gid)) {
            return Result.fail(Result.NULL_ERR, "活动商品id不能为空！");
        }
        // 业务
        return eventGoodsService.deleteEventGoodsById(eid, gid);
    }

    @ApiOperation(value = "删除活动商品（批量）", tags = "活动模块")
    @DeleteMapping("/goods/{eid}")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    Result deleteEventByIds(@PathVariable String eid, @Valid @RequestBody IdsList idsList, @RequestHeader(name = HEADER_NAME) String token, BindingResult result) {
        if (idsList.getIds().size() == 0) {
            return Result.fail(Result.NULL_ERR, "活动ids不能为空！");
        }
        return eventGoodsService.deleteEventGoodsByIds(eid, idsList.getIds());
    }

}
