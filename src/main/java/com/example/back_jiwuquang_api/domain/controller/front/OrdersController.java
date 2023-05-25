package com.example.back_jiwuquang_api.domain.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back_jiwuquang_api.dto.orders.InsertOrderDTO;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.Objects;

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
    /** ******************* 查询 *********************** **/

    @ApiOperation(value = "获取全部订单（分页）", tags = "订单模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @GetMapping("/{page}/{size}")
    Result getOrderPageByDTO(@RequestHeader(name = HEADER_NAME) String token,
                             HttpServletRequest request,
                             @ApiParam("页码") @PathVariable int page,
                             @ApiParam("每页个数") @PathVariable int size,
                             @Valid @RequestBody SelectOrderDTO selectOrderDTO,
                             BindingResult res) {
        if (res.hasErrors()) {
            return Result.fail(res.getFieldError().getDefaultMessage());
        }
        // 业务
        String userId = request.getAttribute(USER_ID_KEY).toString();
        return ordersService.getOrderPageByDTO(page, size, selectOrderDTO, userId, null);
    }

    @ApiOperation(value = "获取订单（分页）", tags = "订单模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @GetMapping("/{status}/{page}/{size}")
    Result getOrderPageByDTO(@RequestHeader(name = HEADER_NAME) String token,
                             HttpServletRequest request,
                             @ApiParam("页码") @PathVariable int page,
                             @ApiParam("每页个数") @PathVariable int size,
                             @ApiParam("状态：0:待付款，1:未发货，2:已发货，3:待收货，4:已收货，5:已评价，6:已取消，7:已超时取消") @PathVariable int status,
                             @Valid @RequestBody SelectOrderDTO selectOrderDTO,
                             BindingResult res) {
        if (res.hasErrors()) {
            return Result.fail(res.getFieldError().getDefaultMessage());
        }
        // 业务
        String userId = request.getAttribute(USER_ID_KEY).toString();
        return ordersService.getOrderPageByDTO(page, size, selectOrderDTO, userId, status);
    }

    @ApiOperation(value = "获取订单详细信息", tags = "订单模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @GetMapping("/{id}")
    Result getOrderAllInfo(@RequestHeader(name = HEADER_NAME) String token,
                           HttpServletRequest request,
                           @ApiParam("订单id") @PathVariable String id) {
        // 业务
        String userId = request.getAttribute(USER_ID_KEY).toString();
        return ordersService.getOrderAllInfo( userId, id);
    }



    /********************* 添加 提交 *************************/
    @ApiOperation(value = "提交订单", tags = "订单模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @PostMapping("/")
    Result pullOrderByDTO(@RequestHeader(name = HEADER_NAME) String token,
                             HttpServletRequest request,
                             @Valid @RequestBody InsertOrderDTO insertOrderDTO,
                             BindingResult res) {
        if (res.hasErrors()) {
            return Result.fail(Objects.requireNonNull(res.getFieldError()).getDefaultMessage());
        }
        // 业务
        String userId = request.getAttribute(USER_ID_KEY).toString();
        return ordersService.addOrderByDTO(insertOrderDTO, userId);
    }





    /********************* 删除 取消 *************************/

    @ApiOperation(value = "删除订单", tags = "订单模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @DeleteMapping("/{id}")
    Result deleteOrderById(@RequestHeader(name = HEADER_NAME) String token,
                           HttpServletRequest request,
                           @ApiParam("订单id") @PathVariable String id) {
        String userId = request.getAttribute(USER_ID_KEY).toString();
        return ordersService.deleteOrderById(userId, id);
    }

}
