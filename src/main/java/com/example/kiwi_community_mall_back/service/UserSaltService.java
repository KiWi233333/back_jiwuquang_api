package com.example.kiwi_community_mall_back.service;

import com.example.kiwi_community_mall_back.dto.user.UserCheckDTO;
import com.example.kiwi_community_mall_back.pojo.sys.UserSalt;
import com.example.kiwi_community_mall_back.repository.UserMapper;
import com.example.kiwi_community_mall_back.repository.UserSaltMapper;
import com.example.kiwi_community_mall_back.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.kiwi_community_mall_back.core.constant.UserConstant.USER_SALT_DTO_KEY;


/**
 * 个人盐值业务层
 *
 * @className: UserSaltService
 * @author: Kiwi2333
 * @date: 2023/4/13 14:54
 */
@Service
public class UserSaltService {
    @Autowired
    UserSaltMapper userSaltMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 获取用户的加密密码和专属盐 （通过用户名/邮箱/手机号 为key存储盐）
     *
     * @param username 用户名/邮箱/手机号
     * @return UserCheckDTO
     */
    public UserCheckDTO getUserSalt(String username,Integer userType) {
        UserCheckDTO userCheckDTO = (UserCheckDTO) redisUtil.get(USER_SALT_DTO_KEY + username);
        if (userCheckDTO == null) {// 空则从数据库取
            return userMapper.selectUserRJoinSaltByUsername(username,userType);
        }
        return userCheckDTO;
    }

    /**
     * 添加用户盐
     *
     * @param userId 用户id
     * @param password 加密后密码
     * @param salt 盐值
     * @return Boolean 是否成功
     */
     public Boolean addUserSalt(String userId, String password, String salt) {
        UserSalt userSalt = new UserSalt(userId, salt);
        UserCheckDTO userCheckDTO = new UserCheckDTO(userId, password, userSalt.getSalt());
        // 2、添加用户盐操作
        if (userSaltMapper.insert(userSalt) > 0) {
            redisUtil.set(USER_SALT_DTO_KEY, userCheckDTO);
            return true;
        } else {
            return false;
        }
    }
}
