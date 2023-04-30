package com.example.kiwi_community_mall_back.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.kiwi_community_mall_back.pojo.wallet.RechargeCombo;

import com.example.kiwi_community_mall_back.pojo.wallet.UserWallet;
import org.apache.ibatis.annotations.Mapper;

/**
 * 充值套餐
 *
 * @className: RechargeComboMapper
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/4/30 22:06
 */
@Mapper
public interface RechargeComboMapper extends BaseMapper<RechargeCombo>{
}