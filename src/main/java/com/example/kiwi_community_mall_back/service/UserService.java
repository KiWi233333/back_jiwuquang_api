package com.example.kiwi_community_mall_back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.kiwi_community_mall_back.dto.user.UserCheckDTO;
import com.example.kiwi_community_mall_back.dto.user.UserRegisterDTO;
import com.example.kiwi_community_mall_back.dto.user.UserTokenDTO;
import com.example.kiwi_community_mall_back.pojo.User;
import com.example.kiwi_community_mall_back.repository.UserMapper;
import com.example.kiwi_community_mall_back.util.BcryptPwdUtil;
import com.example.kiwi_community_mall_back.util.CheckValidUtil;
import com.example.kiwi_community_mall_back.util.JWTUtil;
import com.example.kiwi_community_mall_back.util.Result;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static com.example.kiwi_community_mall_back.constant.UserConstant.*;

/**
 * 描述
 *
 * @className: UsersSercive
 * @author: Author作者
 * @description: TODO描述
 * @date: 2023/4/13 14:54
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserSaltService userSaltService;
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
    public Result toUserLoginByPhoneCode(String phone, String code) {
        // 获取缓存验证码
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
    public Result toUserLoginByEmailCode(String email, String code) {
        // 获取缓存验证码
        String res = String.valueOf(redisTemplate.opsForValue().get(EMAIL_CODE_KEY + email));
        if (StringUtil.isNullOrEmpty(res) || !res.equals(code))
            return Result.fail("验证码错误！");
        // 3、验证
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
        // 4、生成 Token
        if (user != null) {
            userTokenDTO.setId(user.getId());
            String token = JWTUtil.createToken(userTokenDTO);
            redisTemplate.delete(EMAIL_CODE_KEY + email);
            return Result.ok("登录成功！", token);
        }
        return Result.fail("登录失败！");
    }

    // 1) 获取登录手机验证码
    public Result getLoginCodeByPhone(String phone) {
        if (!CheckValidUtil.checkPhone(phone))
            return Result.fail("手机号不合法！");
        // 1、获取缓存
        if (redisTemplate.opsForValue().get(PHONE_CODE_KEY + phone) != null)
            return Result.fail("验证码已发送，请60s后再重试！");
        // 2、验证手机号是否已经被使用
        if (redisTemplate.opsForValue().get(PHONE_MAPS_KEY + phone) == null && userMapper.selectOne(new QueryWrapper<User>().select("phone").eq("phone", phone)) == null) {
            return Result.fail("该手机号未注册！");
        } else {
            redisTemplate.opsForValue().set(PHONE_MAPS_KEY + phone, phone);// 对数据库缓存
        }
        // 3、生成随机数
        String rand = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
        redisTemplate.opsForValue().set(PHONE_CODE_KEY + phone, rand, 61, TimeUnit.SECONDS);
        return Result.ok("获取成功！", rand);
    }

    // 2) 获取登录邮箱验证码
    public Result getLoginCodeByEmail(String email) {
        if (!CheckValidUtil.checkEmail(email))
            return Result.fail("邮箱不合法！");
        // 1、获取缓存
        if (redisTemplate.opsForValue().get(EMAIL_CODE_KEY + email) != null)
            return Result.fail("验证码已发送，请60s后再重试！");
        // 2、验证邮箱是否已经被使用
        if (redisTemplate.opsForValue().get(EMAIL_MAPS_KEY + email) == null && userMapper.selectOne(new QueryWrapper<User>().select("email").eq("email", email)) == null) {
            return Result.fail("该邮箱未注册！");
        } else {
            redisTemplate.opsForValue().set(PHONE_MAPS_KEY + email, email);// 对数据库缓存
        }
        // 3、生成随机数
        String rand = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
        redisTemplate.opsForValue().set(EMAIL_CODE_KEY + email, rand, 61, TimeUnit.SECONDS);
        return Result.ok("获取成功！", rand);
    }


    /**
     * 用户注册
     *
     * @param u
     * @return
     */
    public Result toRegister(UserRegisterDTO u) {
        int flag = 0;
        User user = new User();
        LocalDateTime date = LocalDateTime.now();
        // 1、验证 验证码真伪
        String rCode;
        if (u.getType() == 0) {// 手机号注册
            rCode = (String) redisTemplate.opsForValue().get(PHONE_CHECK_CODE_KEY + u.getPhone());
            if (StringUtil.isNullOrEmpty(rCode) || !rCode.equals(u.getCode())) {// 判断是否一致
                return Result.fail("短信未发送!");
            }
            // 2、判断类型
            user.setUsername(u.getUsername())
                    .setPhone(u.getPhone())
                    .setPassword(u.getPassword())
                    .setCreateTime(date)
                    .setUpdateTime(date)
                    .setNickname("新用户")
                    .setIsPhoneVerified(1)
                    .setAvatar("default.png");
        } else {// 邮箱注册
            rCode = (String) redisTemplate.opsForValue().get(EMAIL_CHECK_CODE_KEY + u.getEmail());
            if (StringUtil.isNullOrEmpty(rCode) || !rCode.equals(u.getCode())) {// 判断是否一致
                return Result.fail("短信未发送!");
            }
            user.setUsername(u.getUsername())
                    .setEmail(u.getUsername())
                    .setPassword(u.getPassword())
                    .setCreateTime(date)
                    .setUpdateTime(date)
                    .setIsEmailVerified(1)
                    .setNickname("新用户")
                    .setAvatar("default.png");
        }
        flag = userMapper.insert(user);
        if (flag > 0) {
            redisTemplate.opsForValue().set(PHONE_MAPS_KEY + user.getPhone(), user.getPhone());
            return Result.ok("注册成功!");
        } else {
            redisTemplate.opsForValue().set(EMAIL_MAPS_KEY + user.getPhone(), user.getPhone());
            return Result.fail("注册失败!");
        }
    }

    // 1) 手机号注册-获取验证码
    public Result getRegisterByPhone(String phone) {
        if (!CheckValidUtil.checkPhone(phone))
            return Result.fail("手机号不合法！");
        String rCode = (String) redisTemplate.opsForValue().get(PHONE_CHECK_CODE_KEY + phone);
        if (StringUtil.isNullOrEmpty(rCode)) return Result.fail("验证码已发送，请60s后再重试！");

        return Result.fail("手机验证码，获取失败！");
    }

    // 2) 邮箱注册-获取验证码
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


//    // 获取缓存的验证码
//    Result getRedisCode(String key) {
//        String rCode = (String) redisTemplate.opsForValue().get(key);
//        if (StringUtil.isNullOrEmpty(rCode)) return Result.fail("验证码已发送，请60s后再重试！");
//        else return null;
//    }
}
