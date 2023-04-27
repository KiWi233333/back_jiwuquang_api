package com.example.kiwi_community_mall_back.constant;

/**
 * 用户Redis keys
 *
 * @className: UserConstant
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/4/27 18:56
 */
public class UserConstant {
    /** 盐表 **/
    public static final String USER_SALT_KEY = "user:salt:";// 用户盐值
    public static final String USER_SALT_MAPS_KEY = "user:salts:";// 用户全部盐表

    /** 验证码 **/
    public static final String PHONE_CODE_KEY = "login:phone:code:";// 登录临时手机号验证码
    public static final String EMAIL_CODE_KEY = "login:email:code:";// 登录临时邮箱验证码
    public static final String PHONE_CHECK_CODE_KEY = "register:phone:code:";// 注册临时手机号验证码
    public static final String EMAIL_CHECK_CODE_KEY = "register:email:code:";// 注册临时邮箱验证码
    /** 用户信息 **/
    public static final String PHONE_MAPS_KEY = "user:phones:";// 手机号
    public static final String EMAIL_MAPS_KEY = "user:emails:";// 邮箱
}
