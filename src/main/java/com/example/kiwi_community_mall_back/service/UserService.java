package com.example.kiwi_community_mall_back.service;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.kiwi_community_mall_back.constant.JwtConstant;
import com.example.kiwi_community_mall_back.constant.UserConstant;
import com.example.kiwi_community_mall_back.dto.user.UserCheckDTO;
import com.example.kiwi_community_mall_back.dto.user.UserRegisterDTO;
import com.example.kiwi_community_mall_back.dto.user.UserTokenDTO;
import com.example.kiwi_community_mall_back.pojo.User;
import com.example.kiwi_community_mall_back.repository.UserMapper;
import com.example.kiwi_community_mall_back.util.*;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.DateUtils;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.example.kiwi_community_mall_back.constant.UserConstant.*;

/**
 * 用户业务
 *
 * @className: UserService
 * @author: Kiwi23333
 * @description: 用户业务
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
    @Autowired
    MailService mailService;


    /**
     * 密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return Result
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
                // 4、缓存token
                // 发送两个token
                if (token != null) {
                    redisTemplate.opsForValue().set(USER_REFRESH_TOKEN_KEY + userTokenDTO.getId(), token, JwtConstant.TOKEN_TIME * 2, TimeUnit.MINUTES);// 30分钟
                    return Result.ok("登录成功！", token);
                }
            } else {
                return Result.fail("密码错误！");
            }
        }
        return Result.fail("无该用户！");
    }

    /**
     * 手机验证码登录 code
     *
     * @param phone 手机号
     * @param code  验证码
     * @return Result
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
            // 更新最后登录时间
            saveLoginTime(userTokenDTO.getId());
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

            // 5、修改登录时间
            saveLoginTime(userTokenDTO.getId());
            return Result.ok("登录成功！", token);
        }
        return Result.fail("登录失败！");
    }

    // 1) 获取登录手机验证码
    public Result getLoginCodeByPhone(String phone) {
        return getCodeByPhone(phone, PHONE_CODE_KEY, 0);
    }

    // 2) 获取登录邮箱验证码
    public Result getLoginCodeByEmail(String email) {
        return getCodeByEmail(email, EMAIL_CODE_KEY, 0);
    }

    // 3) 记录登录时间
    private boolean saveLoginTime(String id) {
        // 更新最后登录时间
        User user = new User()
                .setId(id)
                .setLastLoginTime(new Date());
        int flag = userMapper.updateById(user);
        return flag ==1;
    }

    /**
     * 用户注册
     *
     * @param u UserRegisterDTO对象
     * @return Result
     */
    @Transactional // 开启事务
    public Result toRegister(UserRegisterDTO u) {
        User user = new User();
        Date date = new Date();
        // 1、生成盐和密码加密
        String randSalt = BcryptPwdUtil.getRandomSalt();
        u.setPassword(BcryptPwdUtil.encodeBySalt(u.getPassword(), randSalt));
        // 2、判断注册类型（手机|邮箱） 和 验证 验证码真伪
        String rCode;
        if (u.getType() == 0) {// 手机号注册
            rCode = String.valueOf(redisTemplate.opsForValue().get(PHONE_CHECK_CODE_KEY + u.getPhone()));
            if (StringUtil.isNullOrEmpty(rCode) || !rCode.equals(u.getCode())) {// 判断是否一致
                return Result.fail("验证码错误!");
            }
            user.setUsername(u.getUsername())
                    .setPhone(u.getPhone())
                    .setPassword(u.getPassword())
                    .setCreateTime(date)
                    .setUpdateTime(date)
                    .setNickname("新用户")
                    .setIsPhoneVerified(1)
                    .setAvatar("default.png");
        } else {// 1)邮箱注册
            rCode = String.valueOf(redisTemplate.opsForValue().get(EMAIL_CHECK_CODE_KEY + u.getEmail()));
            if (StringUtil.isNullOrEmpty(rCode) || !rCode.equals(u.getCode())) {// 判断是否一致
                return Result.fail("验证码错误!");
            }
            user.setUsername(u.getUsername())
                    .setEmail(u.getEmail())
                    .setPassword(u.getPassword())
                    .setCreateTime(date)
                    .setUpdateTime(date)
                    .setIsEmailVerified(1)
                    .setNickname("新用户")
                    .setAvatar("default.png");
        }
        if (userMapper.insert(user) <= 0 || Boolean.TRUE.equals(!userSaltService.addUserSalt(user.getId(), user.getPassword(), randSalt))) {
            return Result.fail("注册失败!");
        } else {
            if (u.getType() == 0) redisTemplate.opsForValue().set(PHONE_MAPS_KEY + user.getPhone(), user.getPhone());
            if (u.getType() == 1) redisTemplate.opsForValue().set(EMAIL_MAPS_KEY + user.getEmail(), user.getEmail());
            redisTemplate.opsForValue().set(USERNAME_MAPS_KEY + user.getUsername(), user.getUsername());
            return Result.ok("注册成功!", null);
        }
    }

    // 1) 手机号注册-获取验证码
    public Result getRegisterCodeByPhone(String phone) {
        return getCodeByPhone(phone, PHONE_CHECK_CODE_KEY, 1);// 1注册验证码
    }

    // 2) 邮箱注册-获取验证码
    public Result getRegisterCodeByEmail(String email) {
        return getCodeByEmail(email, EMAIL_CHECK_CODE_KEY, 1);// 1注册验证码
    }


    /**
     * 验证-用户是否存在
     *
     * @param username 用户名
     * @return Result
     */
    public Result checkUserIsExist(String username) {
        if (StringUtil.isNullOrEmpty(username)) return Result.fail("用户名不能为空！");// 判空
        if (!CheckValidUtil.checkUsername(username)) return Result.fail("用户名不合法！");// 用户名不合法
        User user;
        // 缓存是否存在
        if (redisTemplate.opsForValue().get(USERNAME_MAPS_KEY + username) != null) {
            log.info("该用户已存在，用户名Redis");
            return Result.fail("该用户已存在");
        } else {
            user = userMapper.selectOne(new QueryWrapper<User>().select("username").eq("username", username));
        }
        // 数据库查询为不为空并保存redis
        if (user != null) {
            redisTemplate.opsForValue().set(USERNAME_MAPS_KEY + user.getUsername(), user.getUsername());
            return Result.fail("该用户已存在！");
        }

        return Result.ok("用户名可用！", null);
    }

    // 1) 获取手机号验证码，存储至不同的key
    private Result getCodeByPhone(String phone, String KEY, Integer type) {// type 登录0 注册1
        if (!CheckValidUtil.checkPhone(phone))
            return Result.fail("手机号不合法！");
        // 1、获取缓存
        if (redisTemplate.opsForValue().get(KEY + phone) != null)
            return Result.fail("验证码已发送，请60s后再重试！");
        // 2、验证手机号是否已经被使用
        if (type == 0) {// 登录0
            if (userMapper.selectOne(new QueryWrapper<User>().select("phone").eq("phone", phone)) == null) {
                return Result.fail("该手机号未注册！");
            } else {
                redisTemplate.opsForValue().set(PHONE_MAPS_KEY + phone, phone);// 对数据库缓存
            }
        } else {// 注册1
            if (userMapper.selectOne(new QueryWrapper<User>().select("phone").eq("phone", phone)) != null) {
                redisTemplate.opsForValue().set(PHONE_MAPS_KEY + phone, phone);// 对数据库缓存
                return Result.fail("该手机号已经被使用！");
            }
        }
        // 3、生成随机数
        String rand = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
        redisTemplate.opsForValue().set(KEY + phone, rand, 61, TimeUnit.SECONDS);
        return Result.ok("获取成功！", rand);
    }


    // 2) 获取邮箱验证码，存储至不同的key
    private Result getCodeByEmail(String email, String KEY, Integer type) {// type 登录0 注册1
        if (!CheckValidUtil.checkEmail(email))
            return Result.fail("邮箱不合法！");
        // 1、获取缓存
        if (redisTemplate.opsForValue().get(KEY + email) != null && redisTemplate.getExpire(KEY + email) > 240)
            return Result.fail("验证码已发送，请60s后再重试！");
        // 2、验证邮箱是否已经被使用
        if (type == 0) {// 登录
            if (userMapper.selectOne(new QueryWrapper<User>().select("email").eq("email", email)) == null) {
                return Result.fail("该邮箱未注册！");
            } else {
                redisTemplate.opsForValue().set(EMAIL_MAPS_KEY + email, email);// 对数据库缓存邮箱号
            }
        } else {// 注册
            if (userMapper.selectOne(new QueryWrapper<User>().select("email").eq("email", email)) != null) {
                redisTemplate.opsForValue().set(EMAIL_MAPS_KEY + email, email);// 对数据库缓存邮箱号
                return Result.fail("该邮箱已经被使用！");
            }
        }
        // 3、生成随机数 并存储在redis设置有效期61s
        String rand = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
        try {
            mailService.sendCodeMail(email, "验证码", type == 0 ? "登录" : "注册", rand);
            redisTemplate.opsForValue().set(KEY + email, rand, 300, TimeUnit.SECONDS);// 缓存
            return Result.ok("发送成功，请查看邮箱，5分钟有效！", null);// success成功
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.fail("邮件发送失败！");
        }

    }

    /**
     * 退出登录
     *
     * @param token jwt token
     * @return Result
     */
    public Result loginOut(String token) {
        try {
            UserTokenDTO userTokenDTO = JWTUtil.getTokenInfoByToken(token);
            // 删除缓存
            redisTemplate.delete(UserConstant.USER_REFRESH_TOKEN_KEY + userTokenDTO.getId());
            // 更新最后登录时间
            if (saveLoginTime(userTokenDTO.getId())) return Result.ok("退出成功！",null);
            log.info("login out user {}",userTokenDTO.getId());
        } catch (Exception e) {
            return Result.fail("退出失败！");
        }
        return Result.fail("退出失败！");
    }

    /**
     * 用户信息相关
     */
    // 1、获取用户所有信息
    public Result getUserInfoByToken(String token) {
        // 用户token
        UserTokenDTO userTokenDTO;
        try {
            userTokenDTO = JWTUtil.getTokenInfoByToken(token);
        } catch (TokenExpiredException e) {
            return Result.fail("用户过期，请重新登录！");
        } catch (Exception e2) {
            return Result.fail("用户token错误！");
        }
        // 拿缓存
        User user = (User) redisTemplate.opsForValue().get(USER_KEY + userTokenDTO.getId());
        log.info("用户查询");
        // 拿数据库
        if (user == null) {
            user = userMapper.selectById(userTokenDTO.getId());
            if (user == null) {
                return Result.fail("无该用户！");
            }
            redisTemplate.opsForValue().set(USER_KEY + userTokenDTO.getId(), user);
        }
        return Result.ok(user);
    }



}
