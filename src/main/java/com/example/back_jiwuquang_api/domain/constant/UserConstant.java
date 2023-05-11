package com.example.back_jiwuquang_api.domain.constant;

/**
 * 用户Redis keys
 *
 * @className: UserConstant
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/4/27 18:56
 */
public class UserConstant {
    public  static final String USER_ID_KEY = "userId";

    /** 盐表 **/
    public static final String USER_SALT_KEY = "user:salt:";// 用户盐值
    public static final String USER_SALT_DTO_KEY = "user:salts:";// 用户全部盐表

    /** 验证码 **/
    public static final String PHONE_CODE_KEY = "user:login:code:";// 登录临时手机号验证码
    public static final String EMAIL_CODE_KEY = "user:login:code:";// 登录临时邮箱验证码
    public static final String PHONE_CHECK_CODE_KEY = "user:register:code:";// 注册临时手机号验证码
    public static final String EMAIL_CHECK_CODE_KEY = "user:register:code:";// 注册临时邮箱验证码
    /** 用户信息 **/
    public static final String USER_ROLE_KEY = "user:role:";// 角色权限
    public static final String USERNAME_MAPS_KEY = "user:usernames:";// 用户名
    public static final String PHONE_MAPS_KEY = "user:phones:";// 手机号
    public static final String EMAIL_MAPS_KEY = "user:emails:";// 邮箱

    public static final String USER_KEY = "user:id:";// 用户详细信息
    public static final String USER_LIST_KEY = "user:list:";// 用户列表

    public static final String USER_REFRESH_TOKEN_KEY = "user:refresh:token:";// 用户refresh_token
    public static final String USER_ACCESS_TOKEN_KEY = "user:access:token:";// 用户Token

    /** 用户钱包 **/
    public static final String USER_WALLET_KEY = "user:wallet:";// 钱包信息
    public static final String USER_RECHARGE_COMBO_KEY = "user:wallet:combo";// 钱包信息
}
