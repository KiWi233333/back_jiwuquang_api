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
    public static final String PHONE_CODE_KEY = "login:phone:code:";// 登录临时手机号验证码
    public static final String EMAIL_CODE_KEY = "login:email:code:";// 登录临时邮箱验证码
    public static final String PHONE_CHECK_CODE_KEY = "register:phone:code:";// 注册临时手机号验证码
    public static final String EMAIL_CHECK_CODE_KEY = "register:email:code:";// 注册临时邮箱验证码
    public static final String PHONE_MAPS_KEY = "user:phone:maps:";// 手机号
    public static final String EMAIL_MAPS_KEY = "user:email:maps:";// 邮箱
}
