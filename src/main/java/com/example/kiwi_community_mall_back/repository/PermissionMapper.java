package com.example.kiwi_community_mall_back.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.kiwi_community_mall_back.pojo.sys.Permission;
import com.example.kiwi_community_mall_back.pojo.sys.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
