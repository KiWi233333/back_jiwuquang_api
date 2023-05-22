package com.example.back_jiwuquang_api.domain.controller.front;

import com.example.back_jiwuquang_api.dto.orders.SelectOrderDTO;
import com.example.back_jiwuquang_api.pojo.orders.Orders;
import com.example.back_jiwuquang_api.service.event.EventService;
import com.example.back_jiwuquang_api.service.orders.OrdersService;
import com.example.back_jiwuquang_api.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.example.back_jiwuquang_api.domain.constant.JwtConstant.HEADER_NAME;
import static com.example.back_jiwuquang_api.domain.constant.UserConstant.USER_ID_KEY;

/**
 * 订单模块
 *
 * @className: EventController
 * @author: Kiwi23333
 * @description: 订单模块
 * @date: 2023/4/30 22:27
 */
@Slf4j
@Api(value = "订单模块")
@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @ApiOperation(value = "获取用户订单（分页）", tags = "购物车模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @GetMapping("/{page}/{size}")
    Result getOrderPageByDTO(@RequestHeader(name = HEADER_NAME) String token,
                             HttpServletRequest request,
                             @ApiParam("页码") @PathVariable int page,
                             @ApiParam("每页个数") @PathVariable int size,
                             @Valid @RequestBody SelectOrderDTO selectOrderDTO) {
        // 业务
        String userId = request.getAttribute(USER_ID_KEY).toString();
        return ordersService.getOrderPageByDTO(page, size,selectOrderDTO, userId);
    }
}
