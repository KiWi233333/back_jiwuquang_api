package com.example.back_jiwuquang_api.domain.controller.front;

import com.example.back_jiwuquang_api.dto.other.IdsList;
import com.example.back_jiwuquang_api.dto.shopcart.AddShopCartDTO;
import com.example.back_jiwuquang_api.service.shopcart.ShopCartService;
import com.example.back_jiwuquang_api.util.Result;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.example.back_jiwuquang_api.domain.constant.JwtConstant.HEADER_NAME;
import static com.example.back_jiwuquang_api.domain.constant.UserConstant.USER_ID_KEY;


/**
 * 购物车模块
 */
@Slf4j
@Api(value = "购物车模块", tags = {"购物车模块"})
@RestController
@RequestMapping("/user/cart")
public class ShopCartController {

    @Autowired
    ShopCartService shopCartService;


    @ApiOperation(value = "获取用户购物车（分页）", tags = "购物车模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @GetMapping("/{page}/{size}")
    Result getGoodsSkuByGoodsById(@RequestHeader(name = HEADER_NAME) String token,
                                  HttpServletRequest request,
                                  @ApiParam("页码") @PathVariable int page,
                                  @ApiParam("每页个数") @PathVariable int size) {
        // 业务
        return shopCartService.getShopCartPageByUserId(page, size, request.getAttribute(USER_ID_KEY).toString());
    }


    @ApiOperation(value = "添加购物车", tags = "购物车模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @PostMapping("/")
    Result addShopCartByUId(@RequestHeader(name = HEADER_NAME) String token,
                            @Valid @RequestBody AddShopCartDTO addShopCartDTO,
                            BindingResult result,
                            HttpServletRequest request) {
        // 参数校验
        if (result.hasErrors()) {
            return Result.fail(result.getFieldError().getDefaultMessage());
        }
        String userId = request.getAttribute(USER_ID_KEY).toString();
        return shopCartService.addShopCart(addShopCartDTO, userId);
    }



    @ApiOperation(value = "修改购物车", tags = "购物车模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @PutMapping("/{id}")
    Result updateShopCartByDto(@RequestHeader(name = HEADER_NAME) String token,
                               @PathVariable String id,
                               @RequestParam Integer nums,
                               HttpServletRequest request) {
        if (nums==0) return  Result.fail("修改失败，数量不能小于1！");
        String userId = request.getAttribute(USER_ID_KEY).toString();
        return shopCartService.updateShopCartByDto(id,nums, userId);
    }

    @ApiOperation(value = "删除购物车（单个）", tags = "购物车模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @DeleteMapping("/one/{id}")
    Result deleteShopCartByOne(@RequestHeader(name = HEADER_NAME) String token,
                               @PathVariable String id,
                               HttpServletRequest request) {
        // 业务
        String userId = request.getAttribute(USER_ID_KEY).toString();
        return shopCartService.deleteShopCartById(id, userId);
    }
    @ApiOperation(value = "删除购物车（批量）", tags = "购物车模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @DeleteMapping("/some")
    Result deleteShopCartBySome(@RequestHeader(name = HEADER_NAME) String token,
                                @RequestBody IdsList idList,
                                HttpServletRequest request) {
        // 业务
        String userId = request.getAttribute(USER_ID_KEY).toString();
        return shopCartService.deleteShopCartByIds(idList.getIds(), userId);
    }

    @ApiOperation(value = "清空购物车", tags = "购物车模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @DeleteMapping("/all")
    Result deleteShopCartByAll(@RequestHeader(name = HEADER_NAME) String token,
                                HttpServletRequest request) {
        // 业务
        String userId = request.getAttribute(USER_ID_KEY).toString();
        return shopCartService.clearShopCartByUserId( userId);
    }

}
