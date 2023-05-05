package com.example.back_jiwuquang_api.repository.pay;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.back_jiwuquang_api.dto.pay.RechargeComboDTO;
import com.example.back_jiwuquang_api.pojo.pay.RechargeCombo;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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