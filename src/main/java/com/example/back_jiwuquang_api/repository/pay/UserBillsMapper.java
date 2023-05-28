package com.example.back_jiwuquang_api.repository.pay;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.back_jiwuquang_api.pojo.pay.UserBills;
import com.example.back_jiwuquang_api.pojo.pay.UserWallet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

@Mapper
public interface UserBillsMapper extends BaseMapper<UserBills> {


}

