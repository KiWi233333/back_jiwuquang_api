package com.example.kiwi_community_mall_back.pojo.user;

import com.baomidou.mybatisplus.annotation.*;
import com.example.kiwi_community_mall_back.enums.Gender;
import com.example.kiwi_community_mall_back.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("nickname")
    private String nickname;

    /**
     * 性别 （男|女|保密）
     */
    @TableField("gender")
    private Gender gender;

    @TableField("avatar")
    private String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("last_login_time")
    private Date lastLoginTime;

    /**
     * 登录的ip
     */
    @TableField("last_login_ip")
    private String lastLoginIp;

    /**
     * 登录状态：on|off
     */
    @TableField("status")
    private UserStatus status;

    @TableField("is_email_verified")
    private Integer isEmailVerified;

    @TableField("is_phone_verified")
    private Integer isPhoneVerified;

    /**
     * 会员等级
     * 1 普通会员
     * 2 黄金会员
     * 3 钻石会员
     */
    @TableField("member_level")
    private Integer memberLevel;
}