package com.example.kiwi_community_mall_back.service;

import com.example.kiwi_community_mall_back.dto.user.WalletRechargeDTO;
import com.example.kiwi_community_mall_back.pojo.wallet.RechargeCombo;
import com.example.kiwi_community_mall_back.pojo.wallet.UserWallet;
import com.example.kiwi_community_mall_back.repository.RechargeComboMapper;
import com.example.kiwi_community_mall_back.repository.UserWalletMapper;
import com.example.kiwi_community_mall_back.util.RedisUtil;
import com.example.kiwi_community_mall_back.util.Result;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.example.kiwi_community_mall_back.core.constant.UserConstant.USER_RECHARGE_COMBO_KEY;
import static com.example.kiwi_community_mall_back.core.constant.UserConstant.USER_WALLET_KEY;

/**
 * 钱包业务层
 *
 * @className: UserWalletService
 * @author: Kiwi23333
 * @description: 钱包的增删查改
 * @date: 2023/4/30 15:49
 */
@Service
@Slf4j
public class UserWalletService {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserWalletMapper userWalletMapper;

    /**
     * 初始化钱包（插入一条用户数据）
     *
     * @param userId 用户id
     * @return 返回受影响的函数
     */
    public int initUserWallet(String userId) {
        int flag = 0;
        try {
            if (StringUtil.isNullOrEmpty(userId)) {
                return 0;
            }
            UserWallet userWallet = new UserWallet().setUserId(userId);
            flag = userWalletMapper.insert(userWallet);
            if (flag > 0) {
                redisUtil.set(USER_WALLET_KEY + userId, userWallet);
            }
            return flag;
        } catch (Exception e) {
            log.error("插入钱包数据错误error {}", e.getMessage());
            return flag;
        }
    }

    /**
     * 根据id获取钱包信息
     *
     * @param id
     * @return
     */
    public Result getUserWalletById(String id) {
        // 1、获取
        UserWallet userWallet = getWalletById(id);
        // 2、判断
        if (userWallet != null) {
            return Result.ok(userWallet);
        } else {
            return Result.fail("获取失败！");
        }
    }

    /**
     * 充值套餐类
     */
    @Autowired
    RechargeComboMapper rechargeComboMapper;

    /**
     * 获取套餐
     *
     * @return
     */
    public Result getAllRechargeCombo() {
        List<RechargeCombo> list = new ArrayList<>();
        try {
            list = (ArrayList<RechargeCombo>) redisUtil.get(USER_RECHARGE_COMBO_KEY);
        } catch (Exception e) {
            log.error("RechargeCombo error: {}", e.getMessage());
        }
        if (list == null) {
            list = rechargeComboMapper.selectList(null);
            if (!list.isEmpty()) {
                redisUtil.set(USER_RECHARGE_COMBO_KEY, list);
            }
        }

        if (!list.isEmpty()) {
            return Result.ok(list);
        }
        return Result.fail("获取失败！");

    }

    /**
     * 钱包充值
     *
     * @param walletRechargeDTO 数据
     * @param userId            用户id
     * @return Result
     */
    @Transactional(rollbackFor = Exception.class)
    public Result toRechargeByUserId(WalletRechargeDTO walletRechargeDTO, String userId) {
        // 1、获取原有钱包
        UserWallet oldWallet = getWalletById(userId);
        if (oldWallet == null) {
            return Result.fail("充值失败，请稍后再试看！");
        }
        // 2、新钱包
        UserWallet newWallet = new UserWallet().setUserId(userId);
        // 3、判断充值模式
        if (walletRechargeDTO.getType() == 0) {// 1）任意充值
            // 追加余额 追加充值额
            newWallet.setRecharge(oldWallet.getRecharge().add(walletRechargeDTO.getAmount()))// +总充值额
                    .setBalance(oldWallet.getBalance().add(walletRechargeDTO.getAmount()));// +余额
            userWalletMapper.updateById(newWallet);
        } else {// 2）套装id充值
            // 套餐充值
            RechargeCombo rechargeCombo = rechargeComboMapper.selectById(walletRechargeDTO.getId());
            BigDecimal comboAmount = rechargeCombo.getAmount();
            Long comboPoints = rechargeCombo.getPoints();

            newWallet.setRecharge(oldWallet.getRecharge().add(comboAmount))// +总充值额
                    .setBalance(oldWallet.getBalance().add(comboAmount))// +余额
                    .setPoints(oldWallet.getPoints() + comboPoints);// 送积分
            userWalletMapper.updateById(newWallet);
        }

        UserWallet userWallet = userWalletMapper.selectById(userId);// 数据库获取
        redisUtil.set(USER_WALLET_KEY + userId, userWallet);
        return Result.ok("充值成功！", null);
    }


    // 1）获取用户钱包
    private UserWallet getWalletById(String id) {
        UserWallet userWallet = (UserWallet) redisUtil.get(USER_WALLET_KEY + id);
        if (userWallet == null) {
            userWallet = userWalletMapper.selectById(id);// 数据库获取
        }
        if (userWallet != null) {
            redisUtil.set(USER_WALLET_KEY + id, userWallet);
            return userWallet;
        } else {
            return null;
        }
    }
}
