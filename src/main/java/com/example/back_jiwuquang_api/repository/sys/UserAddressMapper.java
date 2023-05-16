package com.example.back_jiwuquang_api.repository.sys;

import com.example.back_jiwuquang_api.pojo.sys.UserAddress;
import com.example.back_jiwuquang_api.repository.SpiceBaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Spliterator;

/**
 * 用户收货地址
 *
 * @className: UserAddressMapper
 * @author: Kiwi23333
 * @description: 用户收货地址
 * @date: 2023/5/16 13:16
 */
@Mapper
public interface UserAddressMapper  extends SpiceBaseMapper<UserAddress> {
}
