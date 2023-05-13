package com.example.back_jiwuquang_api.service.shopcart;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.back_jiwuquang_api.dto.shopcart.AddShopCartDTO;
import com.example.back_jiwuquang_api.pojo.shopcart.ShopCart;
import com.example.back_jiwuquang_api.repository.shopcart.ShopCartMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import com.example.back_jiwuquang_api.vo.ShopCartPageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.back_jiwuquang_api.domain.constant.ShopCartConstant.SHOP_CART_MAPS;

/**
 * 购物车业务层
 *
 * @className: UserWalletService
 * @author: Kiwi23333
 * @description: 购物车的增删查改
 * @date: 2023/4/30 15:49
 */
@Service
@Slf4j
public class ShopCartService {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    ShopCartMapper shopCartMapper;


    /**
     * 分页查询用户购物车
     *
     * @param page   页码
     * @param size   每页个数
     * @param userId 用户id
     * @return Result
     */
    public Result getShopCartPageByUserId(int page, int size, String userId) {
        // 分页查询用户购物车
        IPage<ShopCartPageVO> shopCartPage = null;
        try {
            shopCartPage = (IPage<ShopCartPageVO>) redisUtil.hGet(SHOP_CART_MAPS + userId, page + "" + size);
        } catch (Exception e) {
            log.warn("IPage 购物车分页缓存读取失败，{}", e.getMessage());
        }
        if (shopCartPage == null) {
            shopCartPage = shopCartMapper.selectShopCartPage(page, size, userId);
        }
        if (shopCartPage == null) {
            log.info("{} shop cart null", userId);
            return Result.fail("购物车为空！");
        } else {
            // 2、缓存
            redisUtil.hPut(SHOP_CART_MAPS + userId, page + "" + size, shopCartPage);
            return Result.ok("获取成功！", shopCartPage);
        }
    }


    /**
     * 添加购物车
     *
     * @param addShopCartDTO addShopCartDTO
     * @param userId         用户id
     * @return Result
     */
    public Result addShopCart(AddShopCartDTO addShopCartDTO, String userId) {
        ShopCart shopCart = AddShopCartDTO.toShopCart(addShopCartDTO).setUserId(userId);
        // 查看是否存在
        ShopCart selectCart = shopCartMapper.selectOne(new LambdaQueryWrapper<ShopCart>().eq(ShopCart::getSkuId, addShopCartDTO.getSkuId()));
        // 存在更新数量+1
        if (selectCart != null) {// 更新
            int count = shopCartMapper.update(new ShopCart()
                            .setId(selectCart.getId())
                            .setQuantity(selectCart.getQuantity() + shopCart.getQuantity()),
                    new LambdaQueryWrapper<ShopCart>().eq(ShopCart::getId, selectCart.getId()).eq(ShopCart::getUserId, userId));
            if (count <= 0) {
                log.info("ShopCart  update failed id {}", shopCart.getId());
                return Result.fail("添加失败！");
            }
        } else {// 添加
            if (shopCartMapper.insert(shopCart) <= 0) return Result.fail("添加失败！");
        }
        // 清除缓存
        redisUtil.delete(SHOP_CART_MAPS + userId);
        return Result.ok("添加成功！", null);
    }


    /**
     * 修改购物车
     *
     * @param id     规格id
     * @param nums   数量
     * @param userId 用户id
     * @return Result
     */
    public Result updateShopCartByDto(String id, Integer nums, String userId) {
        // sql updateShopCart
        if (shopCartMapper.update(new ShopCart().setId(id).setQuantity(nums),
                new LambdaQueryWrapper<ShopCart>().eq(ShopCart::getUserId, userId)) <= 0)
            return Result.fail("修改失败！");
        // 清除缓存
        redisUtil.delete(SHOP_CART_MAPS + userId);
        return Result.ok("修改成功！", null);
    }


    /**
     * 删除购物车（单个）
     *
     * @param id     购物车id
     * @param userId 用户id
     * @return Result
     */
    public Result deleteShopCartById(String id, String userId) {
        // 1、sql
        if (shopCartMapper.delete(new LambdaQueryWrapper<ShopCart>().eq(ShopCart::getUserId, userId).eq(ShopCart::getId, id)) <= 0) {
            return Result.fail("删除失败！");
        }
        // 2、清除缓存
        redisUtil.delete(SHOP_CART_MAPS + userId);
        return Result.ok("删除成功！", null);
    }

    /**
     * 删除购物车（批量）
     *
     * @param ids    购物车ids
     * @param userId 用户id
     * @return Result
     */
    public Result deleteShopCartByIds(List<String> ids, String userId) {
        // 1、sql
        int flag = shopCartMapper.delete(new LambdaQueryWrapper<ShopCart>().in(ShopCart::getId, ids).eq(ShopCart::getUserId, userId));
        if (flag <= 0) {
            return Result.fail("删除失败！");
        }
        // 2、清除缓存
        redisUtil.delete(SHOP_CART_MAPS + userId);
        log.info("删除购物车 {} 条! {}", flag, userId);
        return Result.ok("删除成功！", flag);
    }

}
