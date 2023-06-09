package com.example.back_jiwuquang_api.domain.controller.front.event;

import com.example.back_jiwuquang_api.dto.sys.WalletRechargeDTO;
import com.example.back_jiwuquang_api.service.event.EventService;
import com.example.back_jiwuquang_api.service.sys.UserWalletService;
import com.example.back_jiwuquang_api.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 活动模块
 *
 * @className: EventController
 * @author: Kiwi23333
 * @description: 活动模块
 * @date: 2023/4/30 22:27
 */
@Slf4j
@Api(value = "活动模块" )
@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventService eventService;

    @ApiOperation(value = "获取全部活动列表", tags = "活动模块")
    @GetMapping("/list")
    Result getAllEventList() {
        return eventService.getAllEventList(0);
    }
    @ApiOperation(value = "获取活动商品", tags = "活动模块")
    @GetMapping("/goods/{id}")
    Result getEventGoodsList(@PathVariable String id) {
        return eventService.getEventGoodsList(id);
    }

}
