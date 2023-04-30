package com.example.kiwi_community_mall_back.repository;

import com.example.kiwi_community_mall_back.dto.user.UserCheckDTO;
import com.example.kiwi_community_mall_back.pojo.user.User;
import com.example.kiwi_community_mall_back.pojo.user.UserSalt;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends MPJBaseMapper<User> {

    // 查询链表查询用户的 id、盐、密码
    default UserCheckDTO selectUserRJoinSaltByUsername(String username) {
        MPJLambdaWrapper<User> qw = new MPJLambdaWrapper<>();
        qw.select(User::getId, User::getPassword) // 用户表
                .select(UserSalt::getSalt)// 盐表
                .eq("t.username", username).or().eq("t.email", username).or().eq("t.phone", username).rightJoin(UserSalt.class, UserSalt::getUserId, User::getId); // 右表
        // 返回该用户对应的盐值
        return this.selectJoinOne(UserCheckDTO.class, qw);
    }

}
