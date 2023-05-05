package com.example.back_jiwuquang_api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.back_jiwuquang_api.core.constant.JwtConstant;
import com.example.back_jiwuquang_api.core.constant.UserConstant;
import com.example.back_jiwuquang_api.dto.user.UserCheckDTO;
import com.example.back_jiwuquang_api.dto.user.UserRegisterDTO;
import com.example.back_jiwuquang_api.dto.user.UserRolePermissionDTO;
import com.example.back_jiwuquang_api.dto.user.UserTokenDTO;
import com.example.back_jiwuquang_api.pojo.sys.User;
import com.example.back_jiwuquang_api.repository.UserMapper;
import com.example.back_jiwuquang_api.util.*;
import com.example.back_jiwuquang_api.vo.UserVO;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

import static com.example.back_jiwuquang_api.core.constant.UserConstant.*;

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
    MailService mailService;
    @Autowired
    UserWalletService userWalletService;

    @Resource
    RedisUtil redisUtil;

    /**
     * 密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return Result
     */
    public Result toUserLoginByPwd(String username, String password,Integer userType) {
        // 1、获取用户的盐值
        UserCheckDTO userCheckDTO = userSaltService.getUserSalt(username,userType);
        if (userCheckDTO != null) {// 存在该用户
            UserTokenDTO userTokenDTO;
            // x获取角色和权限
            userTokenDTO = (UserTokenDTO) redisUtil.get(USER_ROLE_KEY + userCheckDTO.getId());
            if (userTokenDTO == null) {
                // 查询角色权限信息
                userTokenDTO = userMapper.selectUserRoleAndPermissionByUserId((userCheckDTO.getId()));
                if (userTokenDTO != null) {
                    redisUtil.set(USER_ROLE_KEY + userCheckDTO.getId(), userTokenDTO);
                }
            }
            // 2、用户验证密码(密码验证)
            boolean flag = BcryptPwdUtil.matches(password, // 密码和数据库密码比对校验
                    userCheckDTO.getPassword(),// 数据库
                    userCheckDTO.getSalt());

            // 验证通过
            if (flag) {
                // 3、生成 Token
                // 多端登录
                String token = (String) redisUtil.get(USER_REFRESH_TOKEN_KEY + userTokenDTO.getId());
                if (StringUtil.isNullOrEmpty(token)) {
                    token = JWTUtil.createToken(userTokenDTO);
                }
                // 4、缓存token
                if (token != null) {
                    redisUtil.set(USER_REFRESH_TOKEN_KEY + userTokenDTO.getId(), token, JwtConstant.TOKEN_TIME * 2);// 30分钟
                }
                saveLoginTime(userTokenDTO.getId());
                return Result.ok("登录成功！", token);
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
        String res = String.valueOf(redisUtil.get(PHONE_CODE_KEY + phone));
        if (StringUtil.isNullOrEmpty(res) || !res.equals(code)) return Result.fail("验证码错误！");
        // 3、验证
        UserRolePermissionDTO userRolePermissionDTO = new UserRolePermissionDTO();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("phone", phone));
        // 4、生成 Token
        if (user != null) {
            userRolePermissionDTO.setId(user.getId());
            String token = JWTUtil.createToken(userRolePermissionDTO);
            redisUtil.delete(PHONE_CODE_KEY + phone);
            // 更新最后登录时间
            saveLoginTime(userRolePermissionDTO.getId());
            return Result.ok("登录成功！", token);
        }
        return Result.fail("登录失败！");
    }


    // 邮箱登录
    public Result toUserLoginByEmailCode(String email, String code) {
        // 获取缓存验证码
        String res = String.valueOf(redisUtil.get(EMAIL_CODE_KEY + email));
        if (StringUtil.isNullOrEmpty(res) || !res.equals(code)) return Result.fail("验证码错误！");
        // 3、验证
        UserRolePermissionDTO userRolePermissionDTO = new UserRolePermissionDTO();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
        // 4、生成 Token
        if (user != null) {
            userRolePermissionDTO.setId(user.getId());
            String token = JWTUtil.createToken(userRolePermissionDTO);
           redisUtil.delete(EMAIL_CODE_KEY + email);

            // 5、修改登录时间
            saveLoginTime(userRolePermissionDTO.getId());
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
        User user = new User().setId(id).setLastLoginTime(new Date());
        int flag = userMapper.updateById(user);
        return flag == 1;
    }

    /**
     * 用户注册
     *
     * @param u UserRegisterDTO对象
     * @return Result
     */
    @Transactional(rollbackFor = Exception.class) // 开启事务
    public Result toRegister(UserRegisterDTO u) {
        User user = new User();
        Date date = new Date();
        // 1、生成盐和密码加密
        String randSalt = BcryptPwdUtil.getRandomSalt();
        u.setPassword(BcryptPwdUtil.encodeBySalt(u.getPassword(), randSalt));
        // 2、判断注册类型（手机|邮箱） 和 验证 验证码真伪
        String rCode;
        // 用户基本信息
        user.setUsername(u.getUsername()).setPassword(u.getPassword()).setCreateTime(date).setUpdateTime(date).setNickname("新用户").setAvatar("default.png");
        if (u.getType() == 0) {// 手机号注册
            rCode = String.valueOf(redisUtil.get(PHONE_CHECK_CODE_KEY + u.getPhone()));
            if (StringUtil.isNullOrEmpty(rCode) || !rCode.equals(u.getCode())) {// 判断是否一致
                return Result.fail("验证码错误!");
            }
            // 手机信息
            user.setPhone(u.getPhone()).setIsPhoneVerified(1);
        } else {// 1)邮箱注册
            rCode = String.valueOf(redisUtil.get(EMAIL_CHECK_CODE_KEY + u.getEmail()));
            if (StringUtil.isNullOrEmpty(rCode) || !rCode.equals(u.getCode())) {// 判断是否一致
                return Result.fail("验证码错误!");
            }
            // 邮箱信息
            user.setEmail(u.getEmail()).setIsEmailVerified(1);
        }
        // 插入用户、盐值、钱包信息 （3）
        if (userMapper.insert(user) <= 0
                || Boolean.TRUE.equals(!userSaltService.addUserSalt(user.getId(), user.getPassword(), randSalt))
                || userWalletService.initUserWallet(user.getId()) <= 0) {
            return Result.fail("注册失败!");
        } else {
            // 缓存数据
            if (u.getType() == 0) redisUtil.set(PHONE_MAPS_KEY + user.getPhone(), user.getPhone());
            if (u.getType() == 1) redisUtil.set(EMAIL_MAPS_KEY + user.getEmail(), user.getEmail());
            redisUtil.set(USERNAME_MAPS_KEY + user.getUsername(), user.getUsername());
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
        if (redisUtil.get(USERNAME_MAPS_KEY + username) != null) {
            log.info("该用户已存在，用户名Redis");
            return Result.fail("该用户已存在");
        } else {
            user = userMapper.selectOne(new QueryWrapper<User>().select("username").eq("username", username));
        }
        // 数据库查询为不为空并保存redis
        if (user != null) {
            redisUtil.set(USERNAME_MAPS_KEY + user.getUsername(), user.getUsername());
            return Result.fail("该用户已存在！");
        }

        return Result.ok("用户名可用！", null);
    }

    // 1) 获取手机号验证码，存储至不同的key
    private Result getCodeByPhone(String phone, String KEY, Integer type) {// type 登录0 注册1
        if (!CheckValidUtil.checkPhone(phone)) return Result.fail("手机号不合法！");
        // 1、获取缓存
        if (redisUtil.get(KEY + phone) != null) return Result.fail("验证码已发送，请60s后再重试！");
        // 2、验证手机号是否已经被使用
        if (type == 0) {// 登录0
            if (userMapper.selectOne(new QueryWrapper<User>().select("phone").eq("phone", phone)) == null) {
                return Result.fail("该手机号未注册！");
            } else {
                redisUtil.set(PHONE_MAPS_KEY + phone, phone);// 对数据库缓存
            }
        } else {// 注册1
            if (userMapper.selectOne(new QueryWrapper<User>().select("phone").eq("phone", phone)) != null) {
                redisUtil.set(PHONE_MAPS_KEY + phone, phone);// 对数据库缓存
                return Result.fail("该手机号已经被使用！");
            }
        }
        // 3、生成随机数
        String rand = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
        redisUtil.set(KEY + phone, rand, 61);
        return Result.ok("获取成功！", rand);
    }


    // 2) 获取邮箱验证码，存储至不同的key
    private Result getCodeByEmail(String email, String KEY, Integer type) {// type 登录0 注册1
        if (!CheckValidUtil.checkEmail(email)) return Result.fail("邮箱不合法！");
        // 1、获取缓存
        if (redisUtil.get(KEY + email) != null && redisUtil.getExpire(KEY + email) > 240)
            return Result.fail("验证码已发送，请60s后再重试！");
        // 2、验证邮箱是否已经被使用
        if (type == 0) {// 登录
            if (userMapper.selectOne(new QueryWrapper<User>().select("email").eq("email", email)) == null) {
                return Result.fail("该邮箱未注册！");
            } else {
                redisUtil.set(EMAIL_MAPS_KEY + email, email);// 对数据库缓存邮箱号
            }
        } else {// 注册
            if (userMapper.selectOne(new QueryWrapper<User>().select("email").eq("email", email)) != null) {
                redisUtil.set(EMAIL_MAPS_KEY + email, email);// 对数据库缓存邮箱号
                return Result.fail("该邮箱已经被使用！");
            }
        }
        // 3、生成随机数 并存储在redis设置有效期61s
        String rand = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
        try {
            mailService.sendCodeMail(email, "验证码", type == 0 ? "登录" : "注册", rand);
            redisUtil.set(KEY + email, rand, 300 );// 缓存
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
            UserRolePermissionDTO userRolePermissionDTO = JWTUtil.getTokenInfoByToken(token);
            // 删除缓存
           redisUtil.delete(UserConstant.USER_REFRESH_TOKEN_KEY + userRolePermissionDTO.getId());
            // 更新最后登录时间
            if (saveLoginTime(userRolePermissionDTO.getId())) return Result.ok("退出成功！", null);
            log.info("login out user {}", userRolePermissionDTO.getId());
        } catch (Exception e) {
            return Result.fail("退出失败！");
        }
        return Result.fail("退出失败！");
    }

    /**
     * 用户信息相关
     */
    // 1、获取用户所有信息
    public Result getUserInfoById(String userId) {
        // 拿缓存
        User user = (User) redisUtil.get(USER_KEY + userId);
        log.info("用户查询");
        // 拿数据库
        if (user == null) {
            user = userMapper.selectById(userId);
            if (user == null) {
                return Result.fail("无该用户！");
            }
            redisUtil.set(USER_KEY + userId, user);
        }
        // 修改为用户的数据(排除部分数据)
        return Result.ok(UserVO.formUser(user));
    }

}