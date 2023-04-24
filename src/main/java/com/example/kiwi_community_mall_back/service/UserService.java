package com.example.kiwi_community_mall_back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.kiwi_community_mall_back.dto.user.UserCheckDTO;
import com.example.kiwi_community_mall_back.dto.user.UserRegister;
import com.example.kiwi_community_mall_back.dto.user.UserTokenDTO;
import com.example.kiwi_community_mall_back.pojo.User;
import com.example.kiwi_community_mall_back.pojo.UserSalt;
import com.example.kiwi_community_mall_back.repository.UserMapper;
import com.example.kiwi_community_mall_back.repository.UserSaltMapper;
import com.example.kiwi_community_mall_back.util.BcryptPwdUtil;
import com.example.kiwi_community_mall_back.util.JWTUtil;
import com.example.kiwi_community_mall_back.util.Result;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @className: UsersSercive
 * @author: Author作者
 * @description: TODO描述
 * @date: 2023/4/13 14:54
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserSaltMapper userSaltMapper;
    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 密码登录
     *
     * @param username
     * @param password
     * @return
     */
    public Result toUserLoginByPwd(String username, String password) {
        // 1、获取用户的盐值
        UserCheckDTO userCheckDTO = getUserSalt(username);
        if (userCheckDTO != null) {// 存在该用户
            // 2、用户验证密码
            boolean flag = BcryptPwdUtil.matches(password, // 密码和数据库密码比对校验
                    userCheckDTO.getPassword(),// 数据库
                    userCheckDTO.getSalt());
            // 验证通过
            if (flag) {
                // 3、生成 Token
                UserTokenDTO userTokenDTO = new UserTokenDTO();
                userTokenDTO.setId(userCheckDTO.getId());
                String token = JWTUtil.createToken(userTokenDTO);
                return Result.ok("登录成功！", token);
            } else {
                return Result.fail(20011, "密码错误！");
            }
        }
        return Result.fail("无该用户！");
    }

    // 获取用户的加密密码和专属盐 （通过用户名/邮箱/手机号 为key存储盐）
    @Cacheable(cacheNames = "user_check", key = "#username", unless = "#result==null") // 缓存数据库密码和盐值
    public UserCheckDTO getUserSalt(String username) {
        MPJLambdaWrapper<User> qw = new MPJLambdaWrapper<>();
        qw.select(User::getId, User::getPassword) // 用户表
                .select(UserSalt::getSalt)// 盐表
                .eq("t.username", username)
                .or().eq("t.email", username)
                .or().eq("t.phone", username)
                .rightJoin(UserSalt.class, UserSalt::getUserId, User::getId); // 右表
        // 返回该用户对应的盐值
        return userMapper.selectJoinOne(UserCheckDTO.class, qw);
    }

    /**
     * 验证码登录 code
     *
     * @param phone
     * @param code
     * @return
     */
    public Result toUserLoginByCode(String phone, String code) {
        String token = "";

        return Result.fail("登录失败！");
    }

    /**
     * 获取登录验证码
     *
     * @param phone
     * @return
     */
    public Result getLoginCodeByPhone(String phone) {

        return Result.fail("获取失败！");
    }


    /**
     * 用户注册
     *
     * @param userRegister
     * @return
     */
    public Result toRegister(UserRegister userRegister) {

        return Result.fail("注册失败！");
    }

    /**
     * 手机号注册-获取验证码
     *
     * @param phone
     * @return
     */
    public Result getRegisterByPhone(String phone) {
        return Result.fail("手机验证码，获取失败！");
    }

    /**
     * 邮箱注册-获取验证码
     *
     * @param email
     * @return
     */
    public Result getRegisterByEmail(String email) {
        return Result.fail("邮箱验证码，获取失败！");
    }

    /**
     * 验证-用户是否存在
     * @param username
     * @return
     */
    public Result checkUserIsExist(String username) {
        if (username.trim().equals("") ) return Result.fail(20014,"用户名不能为空");// 判空
        Object userCheck = redisTemplate.opsForValue().get("user_check::"+username);// 获取redis缓存：工具盐判断
        if (userCheck==null) {
            return Result.ok("用户名可用");
        }else {
            User user = userMapper.selectOne(new QueryWrapper<User>().select("username").eq("username",username));
            if (user==null) {
                return Result.ok("用户名可用");
            }
        }
        return Result.fail("该用户已存在");
    }
}
