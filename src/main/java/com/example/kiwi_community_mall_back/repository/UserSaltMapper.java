package com.example.kiwi_community_mall_back.repository;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.kiwi_community_mall_back.pojo.User;
import com.example.kiwi_community_mall_back.pojo.UserSalt;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserSaltMapper extends BaseMapper<UserSalt> {

}
