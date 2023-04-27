package com.example.kiwi_community_mall_back.service;

import com.example.kiwi_community_mall_back.dto.user.UserCheckDTO;
import com.example.kiwi_community_mall_back.pojo.User;
import com.example.kiwi_community_mall_back.pojo.UserSalt;
import com.example.kiwi_community_mall_back.repository.UserMapper;
import com.example.kiwi_community_mall_back.repository.UserSaltMapper;
import com.example.kiwi_community_mall_back.util.BcryptPwdUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
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
     * @param username
     * @return
     */
    public UserCheckDTO getUserSalt(String username) {
        UserCheckDTO userCheckDTO = (UserCheckDTO) redisTemplate.opsForValue().get(USER_SALT_DTO_KEY+username);
        if (userCheckDTO!=null){ // 首先回去redis
            return userCheckDTO;
        }else{
            MPJLambdaWrapper<User> qw = new MPJLambdaWrapper<>();
            qw.select(User::getId, User::getPassword) // 用户表
                    .select(UserSalt::getSalt)// 盐表
                    .eq("t.username", username)
                    .or().eq("t.email", username)
                    .or().eq("t.phone", username)
                    .rightJoin(UserSalt.class, UserSalt::getUserId, User::getId); // 右表
            // 返回该用户对应的盐值
            userCheckDTO =userMapper.selectJoinOne(UserCheckDTO.class, qw);
            return userCheckDTO;
        }

    }

    /**
     * 添加
     * @param userId
     * @return
     */
    Boolean addUserSalt(String userId,String password,String salt) {
        UserSalt userSalt = new UserSalt(userId,salt);
        UserCheckDTO userCheckDTO = new UserCheckDTO(userId,password, userSalt.getSalt());
        // 2、添加用户盐操作
        if (userSaltMapper.insert(userSalt) > 0) {
            redisTemplate.opsForValue().set(USER_SALT_DTO_KEY,userCheckDTO);
            return true;
        } else {
            return false;
        }
    }
}
