package com.example.kiwi_community_mall_back.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.example.kiwi_community_mall_back.vo.Gender;
import com.example.kiwi_community_mall_back.vo.MemberLevel;
import com.example.kiwi_community_mall_back.vo.UserStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("users")
public class Users {

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

    @TableField("gender")
    private Gender gender;

    @TableField("avatar")
    private String avatar;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    @TableField("last_login_ip")
    private String lastLoginIp;

    @TableField("status")
    private UserStatus status;

    @TableField("is_email_verified")
    private Boolean isEmailVerified;

    @TableField("is_phone_verified")
    private Boolean isPhoneVerified;

    @TableField("member_level")
    private MemberLevel memberLevel;
}