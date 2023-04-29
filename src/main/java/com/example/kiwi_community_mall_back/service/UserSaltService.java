package com.example.kiwi_community_mall_back.service;

import com.example.kiwi_community_mall_back.dto.user.UserCheckDTO;
import com.example.kiwi_community_mall_back.pojo.UserSalt;
import com.example.kiwi_community_mall_back.repository.UserMapper;
import com.example.kiwi_community_mall_back.repository.UserSaltMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.example.kiwi_community_mall_back.constant.UserConstant.USER_SALT_DTO_KEY;


/**
 * 用户登录个人盐值
 *
 * @className: UsersSaltSercive
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
    RedisTemplate redisTemplate;

    /**
     * 获取用户的加密密码和专属盐 （通过用户名/邮箱/手机号 为key存储盐）
     *
     * @param username 用户名/邮箱/手机号
     * @return UserCheckDTO
     */
    public UserCheckDTO getUserSalt(String username) {
        UserCheckDTO userCheckDTO = (UserCheckDTO) redisTemplate.opsForValue().get(USER_SALT_DTO_KEY + username);
        if (userCheckDTO == null) {// 空则从数据库取
            return userMapper.selectUserRJoinSaltByUsername(username);
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
            redisTemplate.opsForValue().set(USER_SALT_DTO_KEY, userCheckDTO);
            return true;
        } else {
            return false;
        }
    }
}
