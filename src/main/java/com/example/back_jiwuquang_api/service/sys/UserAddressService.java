package com.example.back_jiwuquang_api.service.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back_jiwuquang_api.dto.other.IdsList;
import com.example.back_jiwuquang_api.dto.pay.RechargeComboDTO;
import com.example.back_jiwuquang_api.dto.sys.UserAddressDTO;
import com.example.back_jiwuquang_api.dto.sys.WalletRechargeDTO;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.pojo.goods.GoodsSku;
import com.example.back_jiwuquang_api.pojo.pay.RechargeCombo;
import com.example.back_jiwuquang_api.pojo.pay.UserWallet;
import com.example.back_jiwuquang_api.pojo.sys.UserAddress;
import com.example.back_jiwuquang_api.repository.pay.RechargeComboMapper;
import com.example.back_jiwuquang_api.repository.pay.UserWalletMapper;
import com.example.back_jiwuquang_api.repository.sys.UserAddressMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.lang.model.element.Element;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.back_jiwuquang_api.domain.constant.UserConstant.*;

/**
 * 收货地址业务层
 *
 * @className: UserWalletService
 * @author: Kiwi23333
 * @description: 收货地址的增删查改
 * @date: 2023/4/30 15:49
 */
@Service
@Slf4j
public class UserAddressService {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserAddressMapper userAddressMapper;


    /**
     * 获取用户地址
     *
     * @param userId 用户id
     * @return Result
     */
    public Result getUserAddressByUserId(String userId) {
        List<UserAddress> addressList = new ArrayList<>();
        // 1、获取缓存
        Map<String, Object> map = redisUtil.hGetAll(USER_ADDRESS_PAGE_KEY + userId);
        if (!map.isEmpty()) {
            for (String key : map.keySet()) {
                addressList.add((UserAddress) map.get(key));
            }
            //先将时间戳转换为LocalDateTime对象，然后再进行排序
            addressList = addressList.stream()
                    .sorted(Comparator.comparing(UserAddress::getUpdateTime).reversed())
                    .collect(Collectors.toList());
            return Result.ok("获取成功！", addressList);
        }
        // 2、sql
        addressList = userAddressMapper.selectList(new LambdaQueryWrapper<UserAddress>().eq(UserAddress::getUserId, userId).orderByDesc(UserAddress::getUpdateTime)); // 调用Mapper接口方法进行分页查询
        // 3、缓存
        map = new HashMap<>();
        for (UserAddress address : addressList) {// 遍历添加map
            map.put(address.getId(), address);
        }
        redisUtil.hPutAll(USER_ADDRESS_PAGE_KEY + userId, map);
        return Result.ok("获取成功！", addressList);
    }

    /**
     * 添加收货地址
     *
     * @param userAddressDTO 参数
     * @param userId         用户id
     * @return Result
     */
    public Result addUserAddress(UserAddressDTO userAddressDTO, String userId) {
        UserAddress userAddress = UserAddressDTO.toUserAddress(userAddressDTO).setUserId(userId);
        if (userAddressMapper.insert(userAddress) < 0) {
            return Result.fail("添加失败！");
        }
        redisUtil.delete(USER_ADDRESS_PAGE_KEY + userId);
        return Result.ok("添加成功！", null);
    }

    /**
     * 修改收货地址
     *
     * @param userAddressDTO 参数
     * @param id             地址id
     * @param userId         用户id
     * @return Result
     */
    public Result updateAddressById(UserAddressDTO userAddressDTO, String id, String userId) {
        UserAddress userAddress = UserAddressDTO.toUserAddress(userAddressDTO)
                .setUserId(userId);
        LambdaQueryWrapper<UserAddress> qw = new LambdaQueryWrapper<UserAddress>()
                .eq(UserAddress::getUserId, userId)
                .eq(UserAddress::getId, id);
        // 1、sql
        if (userAddressDTO.getIsDefault().equals(1)) {
            userAddressMapper.update(new UserAddress().setIsDefault(0), new LambdaQueryWrapper<UserAddress>()
                    .eq(UserAddress::getUserId, userId));
        }
        if (userAddressMapper.update(userAddress, qw) <= 0) {
            return Result.fail("修改失败！");
        }
        // 2、删除全部缓存
        redisUtil.delete(USER_ADDRESS_PAGE_KEY + userId);
        return Result.ok("修改成功！", null);
    }

    /**
     * 删除收货地址
     *
     * @param id     地址id
     * @param userId 用户id
     * @return Result
     */
    public Result deleteAddressById(String id, String userId) {

        // 1、sql
        if (userAddressMapper.delete(new LambdaQueryWrapper<UserAddress>().eq(UserAddress::getUserId, userId).eq(UserAddress::getId, id)) <= 0) {
            return Result.fail("删除失败！");
        }
        // 2、删除缓存
        redisUtil.hDelete(USER_ADDRESS_PAGE_KEY + userId, id);
        return Result.ok("删除成功！", null);
    }

    /**
     * 修改是否默认地址
     *
     * @param id        地址id
     * @param userId    用户id
     * @param isDefault 是否默认
     * @return Result
     */
    public Result updateAddressDefault(String id, String userId, Integer isDefault) {
        if (this.setIsDefault(id, userId, isDefault)) {
            return Result.ok("修改默认成功！", null);
        } else {
            return Result.fail("修改失败！");
        }
    }


    // 修改用户列表
    private boolean setIsDefault(String id, String userId, Integer isDefault) {
        LambdaQueryWrapper<UserAddress> qw = new LambdaQueryWrapper<UserAddress>()
                .eq(UserAddress::getUserId, userId);
        if (isDefault.equals(1))
            userAddressMapper.update(new UserAddress().setIsDefault(0), qw);
        // 修改单条
        if (userAddressMapper.update(new UserAddress().setIsDefault(isDefault.equals(1) ? 1 : 0), qw.eq(UserAddress::getId, id)) <= 0)
            return false;
        redisUtil.delete(USER_ADDRESS_PAGE_KEY + userId);
        return true;
    }


    /**
     * 批量删除地址
     *
     * @param ids
     * @param userId
     * @return
     */
    public Result deleteAddressByIds(List<String> ids, String userId) {
        int flag = userAddressMapper.deleteBatchIds(ids);
        if (flag <= 0) {
            return Result.fail("删除失败！");
        }
        return Result.ok("删除成功！", flag);

    }
}
