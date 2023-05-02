package com.example.kiwi_community_mall_back.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.kiwi_community_mall_back.pojo.sys.Permission;
import com.example.kiwi_community_mall_back.pojo.sys.RolePermission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
}
