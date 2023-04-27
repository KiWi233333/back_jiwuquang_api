package com.example.kiwi_community_mall_back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.kiwi_community_mall_back.dto.user.UserCheckDTO;
import com.example.kiwi_community_mall_back.dto.user.UserRegisterDTO;
import com.example.kiwi_community_mall_back.dto.user.UserTokenDTO;
import com.example.kiwi_community_mall_back.pojo.User;
import com.example.kiwi_community_mall_back.repository.UserMapper;
import com.example.kiwi_community_mall_back.util.BcryptPwdUtil;
import com.example.kiwi_community_mall_back.util.JWTUtil;
import com.example.kiwi_community_mall_back.util.Result;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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
    UserSaltService userSaltService;
    @Autowired
    RedisTemplate redisTemplate;

    // 手机验证码key
    final String PHONE_CODE_KEY = "login:phone:code:";

    /**
     * 密码登录
     *
     * @param username
     * @param password
     * @return
     */
    public Result toUserLoginByPwd(String username, String password) {
        // 1、获取用户的盐值
        UserCheckDTO userCheckDTO = userSaltService.getUserSalt(username);
        if (userCheckDTO != null) {// 存在该用户
            // 2、用户验证密码(密码验证)
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

    /**
     * 手机验证码登录 code
     *
     * @param phone
     * @param code
     * @return
     */
    public Result toUserLoginByCode(String phone, String code) {
        String res = String.valueOf(redisTemplate.opsForValue().get(PHONE_CODE_KEY + phone));
        if (StringUtil.isNullOrEmpty(res) || !res.equals(code))
            return Result.fail("验证码错误！");
        // 3、验证
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("phone", phone));
        // 4、生成 Token
        if (user != null) {
            userTokenDTO.setId(user.getId());
            String token = JWTUtil.createToken(userTokenDTO);
            redisTemplate.delete(PHONE_CODE_KEY + phone);
            return Result.ok("登录成功！", token);
        }
        return Result.fail("登录失败！");
    }

    /**
     * 获取登录手机验证码
     *
     * @param phone
     * @return
     */
    public Result getLoginCodeByPhone(String phone) {
        if (redisTemplate.opsForValue().get(PHONE_CODE_KEY + phone) != null)
            return Result.fail("验证码已发送，请60s后再重试！");
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("phone", phone));
        if (user != null) {
            // 生成随机数
            String rand = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
            redisTemplate.opsForValue().set(PHONE_CODE_KEY + phone, rand, 61, TimeUnit.SECONDS);
            return Result.ok("获取成功！", rand);
        } else {
            return Result.fail("该用户未注册！");
        }
    }

    public Result getLoginCodeByEmail(String email) {
        if (redisTemplate.opsForValue().get(PHONE_CODE_KEY + email) != null)
            return Result.fail("验证码已发送，请60s后再重试！");
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
        if (user != null) {
            // 生成随机数
            String rand = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
            redisTemplate.opsForValue().set(PHONE_CODE_KEY + email, rand, 61, TimeUnit.SECONDS);
            return Result.ok("获取成功！", rand);
        } else {
            return Result.fail("该用户未注册！");
        }
    }


    /**
     * 用户注册
     *
     * @param userRegisterDTO
     * @return
     */
    public Result toRegister(UserRegisterDTO userRegisterDTO) {
        if (userRegisterDTO.getType() == 0) {// 手机号注册
            User user = new User();
            userMapper.insert(user);
        } else {// 邮箱注册

        }

        return Result.ok("注册成功!");
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
     *
     * @param username
     * @return
     */
    public Result checkUserIsExist(String username) {
        if (StringUtil.isNullOrEmpty(username)) return Result.fail(20014, "用户名不能为空");// 判空
        Object userCheck = redisTemplate.opsForValue().get(userSaltService.USER_SALT + username);// 获取redis缓存：工具盐判断
        if (userCheck == null) {
            return Result.ok("用户名可用");
        } else {
            User user = userMapper.selectOne(new QueryWrapper<User>().select("username").eq("username", username));
            if (user == null) {
                return Result.ok("用户名可用");
            }
        }
        return Result.fail("该用户已存在");
    }

    // 退出登录
    public Result loginOut(String phone) {
        return Result.ok();
    }
}
