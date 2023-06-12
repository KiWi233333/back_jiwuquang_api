package com.example.back_jiwuquang_api.domain.controller.admin;

import com.example.back_jiwuquang_api.dto.orders.DeliveryDTO;
import com.example.back_jiwuquang_api.dto.orders.SelectAllUserOrderDTO;
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

/**
 * 管理员模块
 * # 订单管理
 *
 * @className: AdminOrdersController
 * @author: Kiwi23333
 * @description: 订单管理
 * @date: 2023/4/30 22:27
 */
@Slf4j
@Api(value = "订单管理")
@RestController
@RequestMapping("/admin/orders")
public class AdminOrdersController {
    /**
     * ****************** 查询 ***********************
     **/
    @Autowired
    OrdersService ordersService;

    @ApiOperation(value = "获取订单（分页）", tags = "订单管理")
    @ApiImplicitParam(name = HEADER_NAME, value = "管理员token", required = true)
    @PostMapping("/list/{page}/{size}")
    Result getOrderPageByDTO(@RequestHeader(name = HEADER_NAME) String token,
                             @ApiParam("页码") @PathVariable int page,
                             @ApiParam("每页个数") @PathVariable int size,
                             @Valid @RequestBody SelectAllUserOrderDTO selectAllUserOrderDTO,
                             BindingResult res) {
        if (res.hasErrors()) {
            return Result.fail(Objects.requireNonNull(res.getFieldError()).getDefaultMessage());
        }
        // 业务
        return ordersService.getAdminOrderPageByDTO(page, size, selectAllUserOrderDTO);
    }

    @ApiOperation(value = "获取订单详细信息", tags = "订单管理")
    @ApiImplicitParam(name = HEADER_NAME, value = "管理员token", required = true)
    @GetMapping("/{userId}/{id}")
    Result getOrderAllInfo(@RequestHeader(name = HEADER_NAME) String token,
                           @ApiParam("管理员id") @PathVariable String userId,
                           @ApiParam("订单id") @PathVariable String id) {
        return ordersService.getOrderAllInfo(userId, id);
    }

    @ApiOperation(value = "订单发货", tags = "订单管理")
    @ApiImplicitParam(name = HEADER_NAME, value = "管理员token", required = true)
    @PostMapping("/delivery/{userId}/{id}")
    Result addDeliveryByOrderId(@RequestHeader(name = HEADER_NAME) String token,
                                @ApiParam("订单id") @PathVariable String id,
                                @ApiParam("用户id") @PathVariable String userId,
                                @Valid @RequestBody DeliveryDTO dto) {
        return ordersService.addDeliveryByOrderId(userId, id,dto);
    }

    @ApiOperation(value = "同意订单退款", tags = "订单管理")
    @ApiImplicitParam(name = HEADER_NAME, value = "管理员token", required = true)
    @PutMapping("/refund/{userId}/{id}")
    Result refundOrderById(@RequestHeader(name = HEADER_NAME) String token,
                           HttpServletRequest request,
                           @ApiParam("管理员id") @PathVariable String userId,
                           @ApiParam("订单id") @PathVariable String id) {
        try {
            return ordersService.refundOrderById(userId, id);
        } catch (Exception e) {
            log.warn("orders订单同意退款失败，{}", e.getMessage());
            return Result.fail(Result.UPDATE_ERR, e.getMessage());
        }
    }
}
